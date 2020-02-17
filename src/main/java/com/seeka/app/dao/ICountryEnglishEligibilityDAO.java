package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.CountryEnglishEligibility;

public interface ICountryEnglishEligibilityDAO {
	public List<CountryEnglishEligibility> getAll();
	public CountryEnglishEligibility get(String id);
	public void save(CountryEnglishEligibility obj);
	public List<CountryEnglishEligibility> getEnglishEligibiltyList(String countryId);
}
