package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.CityImages;
import com.seeka.app.dto.ImageResponseDto;
import com.seeka.app.exception.ValidationException;

public interface ICityImagesService {

	void save(CityImages obj);

	void update(CityImages obj);

	CityImages get(BigInteger id);

	List<CityImages> getAll();

	void saveCityImage(BigInteger categoryId, String imageName);

	List<ImageResponseDto> getCityImageListBasedOnCityId(BigInteger cityId);

	String deleteCityImage(BigInteger id) throws ValidationException;
}
