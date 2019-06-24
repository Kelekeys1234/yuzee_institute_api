package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CountryEnglishEligibility;



public interface ICountryEnglishEligibilityService {
	public List<CountryEnglishEligibility> getAll();
	public CountryEnglishEligibility get(BigInteger id);
	public void save(CountryEnglishEligibility obj); 
	public List<CountryEnglishEligibility> getEnglishEligibiltyList(BigInteger countryId);
}
 