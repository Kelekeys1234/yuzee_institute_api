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
	private ICountryJobSitesDAO iCountryJobSitesDAO;
	
	@Override
	public void save(CountryJobSites obj) {
		iCountryJobSitesDAO.save(obj);
		
	}
	
	@Override
	public List<CountryJobSites> getAll() {		
		return iCountryJobSitesDAO.getAll();
	}
    
	@Override
	public CountryJobSites get(BigInteger id) {
		return iCountryJobSitesDAO.get(id);
	}
	
	@Override
	public List<CountryDto> getAllUniversityCountries(){
		return iCountryJobSitesDAO.getAllUniversityCountries();
	}
	
	@Override
	public List<CountryDto> searchInterestByCountry(String name) {		
		return iCountryJobSitesDAO.searchInterestByCountry(name);
	}
	
	@Override
	public List<CountryDto> getAllCountries(){
		return iCountryJobSitesDAO.getAllCountries();
	}
}
