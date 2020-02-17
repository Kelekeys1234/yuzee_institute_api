package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Country;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.DiscoverCountryDto;

public interface ICountryDAO {

	List<Country> getAll();

	Country get(String id);

	List<CountryDto> getAllUniversityCountries();

	Country save(Country obj);

	List<CountryDto> searchInterestByCountry(String name);

	List<CountryDto> getAllCountries();

	List<CountryDto> getAllCountryName();

	List<DiscoverCountryDto> getDiscoverCountry();

	List<CountryDto> autoSearch(int i, int j, String searchKey);

	Country getCountryBasedOnCitizenship(String citizenship);

	List<Country> getCountryIdsBasedOnCitizenships(List<String> citizenships);

	List<Country> getAllCountryByIds(List<String> countryIds);

	Country getCountryByName(String countryName);
}
