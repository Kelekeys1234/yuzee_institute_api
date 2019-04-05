package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dto.CountryDto;
 

@Service
@Transactional
public class CountryService implements ICountryService{
	
	@Autowired
	ICountryDAO countryDAO;
	
	@Override
	public void save(Country obj) {
		countryDAO.save(obj);
		
	}
	
	@Override
	public List<Country> getAll() {		
		return countryDAO.getAll();
	}
    
	@Override
	public Country get(Integer id) {
		return countryDAO.get(id);
	}
	
	@Override
	public List<CountryDto> getAllUniversityCountries(){
		return countryDAO.getAllUniversityCountries();
	}
	
	@Override
	public List<CountryDto> searchInterestByCountry(String name) {		
		return countryDAO.searchInterestByCountry(name);
	}
	
	@Override
	public List<CountryDto> getAllCountries(){
		return countryDAO.getAllCountries();
	}
}
