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

import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MediaService implements IMediaService {

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public String uploadImage(final MultipartFile file, final BigInteger entityId, final String entityType, final String type) {
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

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL).queryParam("entityId", entityId)
				.queryParam("entityType", entityType);
		if (type != null) {
			builder.queryParam("type", type);
		}

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formDate, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(builder.toUriString(), request, Map.class);
		newFile.delete();
		Object data = response.getBody().get("data");
		return String.valueOf((String) data);

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

}
