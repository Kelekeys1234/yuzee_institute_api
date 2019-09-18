package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteImages;
import com.seeka.app.dao.IInstituteImagesDAO;
import com.seeka.app.dto.ImageResponseDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional
public class InstituteImagesService implements IInstituteImagesService {

	@Autowired
	private IInstituteImagesDAO iInstituteImagesDAO;

	@Autowired
	private IInstituteService iInstituteService;

	@Value("${s3.url}")
	private String s3URL;

	@Override
	public void save(final InstituteImages obj) {
		iInstituteImagesDAO.save(obj);
	}

	@Override
	public void update(final InstituteImages obj) {
		iInstituteImagesDAO.update(obj);
	}

	@Override
	public InstituteImages get(final BigInteger id) {
		return iInstituteImagesDAO.get(id);
	}

	@Override
	public List<InstituteImages> getAll() {
		return iInstituteImagesDAO.getAll();
	}

	@Override
	public void saveInstituteImage(final BigInteger instituteId, final String imageName, final String subCategory) throws ValidationException {
		InstituteImages instituteImages = new InstituteImages();
		Institute institute = iInstituteService.get(instituteId);
		if (institute == null) {
			throw new ValidationException("Institute not found for id" + instituteId);
		}
		instituteImages.setInstitute(institute);
		instituteImages.setImageName(imageName);
		instituteImages.setIsActive(true);
		instituteImages.setSubCategory(subCategory);
		instituteImages.setCreatedOn(new Date());
		instituteImages.setCreatedBy("API");
		iInstituteImagesDAO.save(instituteImages);
	}

	@Override
	public List<ImageResponseDto> getInstituteImageListBasedOnId(final BigInteger instituteId) {
		List<InstituteImages> instituteImages = iInstituteImagesDAO.getInstituteImageListBasedOnId(instituteId);
		List<ImageResponseDto> resultList = new ArrayList<>();
		for (InstituteImages instituteImages2 : instituteImages) {
			ImageResponseDto imageResponseDto = new ImageResponseDto();
			imageResponseDto.setCategoryId(instituteImages2.getInstitute().getId());
			imageResponseDto.setId(instituteImages2.getId());
			imageResponseDto.setImageName(instituteImages2.getImageName());
			imageResponseDto.setBaseUrl(s3URL);
			imageResponseDto.setCategory(ImageCategory.INSTITUTE.name());
			imageResponseDto.setSubCategory(instituteImages2.getSubCategory());
			resultList.add(imageResponseDto);
		}
		return resultList;
	}

	@Override
	public String deleteInstituteImage(final BigInteger id) {
		InstituteImages instituteImages = iInstituteImagesDAO.get(id);
		instituteImages.setIsActive(false);
		iInstituteImagesDAO.save(instituteImages);
		return instituteImages.getImageName();
	}

}
