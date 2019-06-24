package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CountryEnglishEligibility;
import com.seeka.app.dao.ICountryEnglishEligibilityDAO;
 

@Service
@Transactional
public class CountryEnglishEligibilityService implements ICountryEnglishEligibilityService{
	
	@Autowired
	ICountryEnglishEligibilityDAO dao;
	
	@Override
	public void save(CountryEnglishEligibility obj) {
		dao.save(obj);
	}
	
	@Override
	public List<CountryEnglishEligibility> getAll() {		
		return dao.getAll();
	}
    
	@Override
	public CountryEnglishEligibility get(BigInteger id) {
		return dao.get(id);
	}
	
	public List<CountryEnglishEligibility> getEnglishEligibiltyList(BigInteger countryId){
		return dao.getEnglishEligibiltyList(countryId);
	}
	
}
