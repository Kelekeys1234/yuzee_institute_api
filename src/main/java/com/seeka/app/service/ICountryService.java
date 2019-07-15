package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CountryRequestDto;

public interface ICountryService {
    public List<Country> getAll();

    public Country get(BigInteger id);

    public List<CountryDto> getAllUniversityCountries();

    public List<CountryDto> searchInterestByCountry(String name);

    public List<CountryDto> getAllCountries();

    public List<CountryDto> getAllCountryName();

    public Map<String,Object> save(CountryRequestDto countryRequestDto);

	/**
	 * @return
	 */
	List<CountryDto> getAllCountryWithCities();
}
