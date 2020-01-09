package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CountryEmployers;
import com.seeka.app.dto.CountryDto;



public interface ICountryEmployerService {
	public List<CountryEmployers> getAll();
	public CountryEmployers get(BigInteger id);
	public List<CountryDto> getAllUniversityCountries();
	public void save(CountryEmployers obj); 
	public List<CountryDto> searchInterestByCountry(String name);
	public List<CountryDto> getAllCountries();
}
 