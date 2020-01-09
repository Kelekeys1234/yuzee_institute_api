package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CountryEnglishEligibility;

public interface ICountryEnglishEligibilityDAO {
	public List<CountryEnglishEligibility> getAll();
	public CountryEnglishEligibility get(BigInteger id);
	public void save(CountryEnglishEligibility obj);
	public List<CountryEnglishEligibility> getEnglishEligibiltyList(BigInteger countryId);
}
