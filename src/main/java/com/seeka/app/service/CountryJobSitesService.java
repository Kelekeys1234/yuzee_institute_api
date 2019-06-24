package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CountryJobSites;
import com.seeka.app.dao.ICountryJobSitesDAO;
import com.seeka.app.dto.CountryDto;
 

@Service
@Transactional
public class CountryJobSitesService implements ICountryJobSitesService{
	
	@Autowired
	ICountryJobSitesDAO dao;
	
	@Override
	public void save(CountryJobSites obj) {
		dao.save(obj);
		
	}
	
	@Override
	public List<CountryJobSites> getAll() {		
		return dao.getAll();
	}
    
	@Override
	public CountryJobSites get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<CountryDto> getAllUniversityCountries(){
		return dao.getAllUniversityCountries();
	}
	
	@Override
	public List<CountryDto> searchInterestByCountry(String name) {		
		return dao.searchInterestByCountry(name);
	}
	
	@Override
	public List<CountryDto> getAllCountries(){
		return dao.getAllCountries();
	}
}
