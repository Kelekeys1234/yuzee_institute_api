package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CountryJobSites;
import com.seeka.app.dto.CountryDto;

public interface ICountryJobSitesDAO {
	
	public List<CountryJobSites> getAll();
	public CountryJobSites get(BigInteger id);
	public List<CountryDto> getAllUniversityCountries();
	public void save(CountryJobSites obj);
	public List<CountryDto> searchInterestByCountry(String name);
	public List<CountryDto> getAllCountries();
}
