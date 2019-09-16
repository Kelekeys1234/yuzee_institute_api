package com.seeka.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MediaService implements IMediaService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IEnrollmentService iEnrollmentService;

	@Autowired
	private IInstituteImagesService iInstituteImagesService;

	@Autowired
	private IInstituteService iInstituteService;

	@Autowired
	private ICityImagesService iCityImagesService;

	@SuppressWarnings("rawtypes")
	@Override
	public String uploadImage(final MultipartFile file, final BigInteger categoryId, final String category, final String subCategory) {
		/**
		 * Set http headers
		 */
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		/**
		 * Form-data body
		 */
		MultiValueMap<String, Object> formDate = new LinkedMultiValueMap<>();
		File newFile = convert(file);
		formDate.add("file", new FileSystemResource(newFile));

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL).queryParam("categoryId", categoryId)
				.queryParam("category", category);
		if (subCategory != null) {
			builder.queryParam("subCategory", subCategory);
		}

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formDate, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(builder.toUriString(), request, Map.class);
		newFile.delete();
		return String.valueOf(response.getBody().get("fileName"));

	}

	private static File convert(final MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convFile;
	}

	@Override
	public String addCategoryImage(final MultipartFile file, final String category, final String subCategory, final BigInteger categoryId)
			throws ValidationException {
		String imageName = null;
		if (category.equals(ImageCategory.ENROLLMENT.name())) {
			imageName = uploadImage(file, categoryId, ImageCategory.ENROLLMENT.name(), subCategory);
			iEnrollmentService.saveEnrollmentImage(categoryId, subCategory, imageName);
		} else if (category.equals(ImageCategory.INSTITUTE.name())) {
			imageName = uploadImage(file, categoryId, ImageCategory.INSTITUTE.name(), subCategory);
			if (subCategory.equals("LOGO")) {
				iInstituteService.updateInstituteImage(categoryId, imageName);
			} else {
				iInstituteImagesService.saveInstituteImage(categoryId, imageName);
			}
		} else if (category.equals(ImageCategory.CITY.name())) {
			imageName = uploadImage(file, categoryId, ImageCategory.CITY.name(), subCategory);
			iCityImagesService.saveCityImage(categoryId, imageName);
		}

		return imageName;
	}

	@Override
	public String deleteCategoryImage(final BigInteger id, final String category, final String subCategory, final BigInteger categoryId)
			throws ValidationException {
		String imageName = null;
		if (category.equals(ImageCategory.ENROLLMENT.name())) {
			imageName = iEnrollmentService.removeEnrollmentImage(id);
			deleteImage(imageName);
		} else if (category.equals(ImageCategory.INSTITUTE.name())) {
			if (subCategory.equals("LOGO")) {
				imageName = iInstituteService.deleteInstituteImage(categoryId);
			} else {
				imageName = iInstituteImagesService.deleteInstituteImage(id);
			}
			deleteImage(imageName);
		} else if (category.equals(ImageCategory.CITY.name())) {
			imageName = iCityImagesService.deleteCityImage(id);
			deleteImage(imageName);
		}

		return imageName;
	}

	private void deleteImage(final String imageName) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL).queryParam("fileName", imageName);
		restTemplate.delete(builder.toUriString());
	}

}
