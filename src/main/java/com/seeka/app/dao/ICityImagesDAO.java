package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.CityImages;

public interface ICityImagesDAO {
	void save(CityImages obj);

	void update(CityImages obj);

	CityImages get(BigInteger id);

	List<CityImages> getAll();

	List<CityImages> getCityImageListBasedOnCityId(BigInteger cityId);
}
