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

    public Map<String, Object> save(CountryRequestDto countryRequestDto);

    /**
     * @return
     */
    List<CountryDto> getAllCountryWithCities();

    public Map<String, Object> getAllDiscoverCountry();

    public Map<String, Object> getCountryDetailsById(BigInteger id);

    public Map<String, Object> getCountryLevelFaculty();

    public Map<String, Object> autoSearch(String searchKey);

    public Map<String, Object> getCourseCountry();

	Country getCountryBasedOnCitizenship(String citizenship);
	
	List<Country> getCountryListBasedOnCitizenship(List<String> citizenships);

	List<BigInteger> getCountryBasedOnCitizenship(List<String> countryNames);
}
