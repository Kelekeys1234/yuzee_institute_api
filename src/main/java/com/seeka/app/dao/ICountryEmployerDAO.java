package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.CountryEmployers;
import com.seeka.app.dto.CountryDto;

public interface ICountryEmployerDAO {
	
	public List<CountryEmployers> getAll();
	public CountryEmployers get(String id);
	public List<CountryDto> getAllUniversityCountries();
	public void save(CountryEmployers obj);
	public List<CountryDto> searchInterestByCountry(String name);
	public List<CountryDto> getAllCountries();
}
