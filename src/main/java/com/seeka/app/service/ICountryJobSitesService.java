package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.CountryJobSites;
import com.seeka.app.dto.CountryDto;



public interface ICountryJobSitesService {
	public List<CountryJobSites> getAll();
	public CountryJobSites get(String id);
	public List<CountryDto> getAllUniversityCountries();
	public void save(CountryJobSites obj); 
	public List<CountryDto> searchInterestByCountry(String name);
	public List<CountryDto> getAllCountries();
}
 