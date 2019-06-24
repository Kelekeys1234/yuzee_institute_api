package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CountryEmployers;
import com.seeka.app.dao.ICountryEmployerDAO;
import com.seeka.app.dto.CountryDto;
 

@Service
@Transactional
public class CountryEmployerService implements ICountryEmployerService{
	
	@Autowired
	ICountryEmployerDAO dao;
	
	@Override
	public void save(CountryEmployers obj) {
		dao.save(obj);
		
	}
	
	@Override
	public List<CountryEmployers> getAll() {		
		return dao.getAll();
	}
    
	@Override
	public CountryEmployers get(BigInteger id) {
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
