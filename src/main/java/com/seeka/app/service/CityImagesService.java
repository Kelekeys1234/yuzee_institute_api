package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.City;
import com.seeka.app.bean.CityImages;
import com.seeka.app.dao.ICityImagesDAO;
import com.seeka.app.dto.ImageResponseDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional
public class CityImagesService implements ICityImagesService {

	@Autowired
	private ICityService iCityService;

	@Autowired
	private ICityImagesDAO iCityImagesDAO;

	@Value("${s3.url}")
	private String s3URL;

	@Override
	public void save(final CityImages obj) {
		iCityImagesDAO.save(obj);
	}

	@Override
	public void update(final CityImages obj) {
		iCityImagesDAO.update(obj);
	}

	@Override
	public CityImages get(final BigInteger id) {
		return iCityImagesDAO.get(id);
	}

	@Override
	public List<CityImages> getAll() {
		return iCityImagesDAO.getAll();
	}

	@Override
	public void saveCityImage(final BigInteger cityId, final String imageName) {
		City city = iCityService.get(cityId);
		CityImages cityImages = new CityImages();
		cityImages.setCity(city);
		cityImages.setImageName(imageName);
		cityImages.setIsActive(true);
		cityImages.setCreatedOn(new Date());
		cityImages.setCreatedBy("API");
		iCityImagesDAO.save(cityImages);
	}

	@Override
	public List<ImageResponseDto> getCityImageListBasedOnCityId(final BigInteger cityId) {
		List<CityImages> cityImages = iCityImagesDAO.getCityImageListBasedOnCityId(cityId);
		List<ImageResponseDto> resultList = new ArrayList<>();
		for (CityImages cityImages2 : cityImages) {
			ImageResponseDto imageResponseDto = new ImageResponseDto();
			imageResponseDto.setCategoryId(cityImages2.getCity().getId());
			imageResponseDto.setId(new BigInteger(String.valueOf(cityImages2.getId())));
			imageResponseDto.setImageName(cityImages2.getImageName());
			imageResponseDto.setBaseUrl(s3URL);
			imageResponseDto.setCategory(ImageCategory.INSTITUTE.name());
			resultList.add(imageResponseDto);
		}
		return resultList;
	}

	@Override
	public String deleteCityImage(final BigInteger id) throws ValidationException {
		CityImages cityImages = iCityImagesDAO.get(id);
		if (cityImages == null) {
			throw new ValidationException("city images not found for id " + id);
		}
		cityImages.setIsActive(false);
		return cityImages.getImageName();
	}

}
