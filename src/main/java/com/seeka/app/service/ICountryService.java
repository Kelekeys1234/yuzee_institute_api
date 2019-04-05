package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;



public interface ICountryService {
	public List<Country> getAll();
	public Country get(Integer id);
	public List<CountryDto> getAllUniversityCountries();
	public void save(Country obj); 
	public List<CountryDto> searchInterestByCountry(String name);
	public List<CountryDto> getAllCountries();
}
 