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
	private ICountryEnglishEligibilityDAO iCountryEnglishEligibilityDAO;
	
	@Override
	public void save(CountryEnglishEligibility countryEnglishEligibility) {
		iCountryEnglishEligibilityDAO.save(countryEnglishEligibility);
	}
	
	@Override
	public List<CountryEnglishEligibility> getAll() {		
		return iCountryEnglishEligibilityDAO.getAll();
	}
    
	@Override
	public CountryEnglishEligibility get(BigInteger id) {
		return iCountryEnglishEligibilityDAO.get(id);
	}
	
	public List<CountryEnglishEligibility> getEnglishEligibiltyList(BigInteger countryId){
		return iCountryEnglishEligibilityDAO.getEnglishEligibiltyList(countryId);
	}
	
}
