package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.City;
import com.seeka.app.dto.CityDto;
import com.seeka.app.dto.UpdateCityDto;

public interface ICityService {
	List<City> getAll();

	City get(BigInteger id);

	List<City> getAllCitiesByCountry(BigInteger countryId);

	void save(City obj);

	List<City> getAllMultipleCitiesByCountry(String countryId);

	Map<String, Object> save(CityDto city);

	Map<String, Object> update(BigInteger id, UpdateCityDto city);

	List<String> getAllCityNames(Integer pageNumber, Integer pageSize, String searchString);
	
	Integer getAllCityNamesCount(String searchString);
}
