package com.seeka.app.dao;

import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.City;

public interface ICityDAO {
     List<City> getAll();

     City get(BigInteger id);

     List<City> getAllCitiesByCountry(BigInteger countryId);

     void save(City obj);

     List<City> getAllMultipleCitiesByCountry(String BigIntegers);

	List<City> getAllCityByIds(List<BigInteger> cityIds);

	List<String> getAllCityNames(Integer pageNumber, Integer pageSize, String searchString);
	
	Integer getAllCityNamesCount(String searchString);
}
