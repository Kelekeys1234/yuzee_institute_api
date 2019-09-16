package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.InstituteImages;
import com.seeka.app.dto.ImageResponseDto;
import com.seeka.app.exception.ValidationException;

public interface IInstituteImagesService {

	void save(InstituteImages obj);

	void update(InstituteImages obj);

	InstituteImages get(BigInteger id);

	List<InstituteImages> getAll();

	void saveInstituteImage(BigInteger instituteId, String imageName) throws ValidationException;

	List<ImageResponseDto> getInstituteImageListBasedOnId(BigInteger instituteId);

	String deleteInstituteImage(BigInteger id);
}
