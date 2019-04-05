package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;

public interface ICountryDAO {
	
	public List<Country> getAll();
	public Country get(Integer id);
	public List<CountryDto> getAllUniversityCountries();
	public void save(Country obj);
	public List<CountryDto> searchInterestByCountry(String name);
	public List<CountryDto> getAllCountries();
}
