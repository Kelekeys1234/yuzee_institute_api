package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Currency;

public interface ICurrencyService {
	
	public void save(Currency obj);
	public void update(Currency obj);
	public Currency get(Integer id);
	public List<Currency> getAll();
	public List<Currency> getCourseTypeByCountryId(Integer countryID);
	public List<Currency> getCurrencyByCountryId(Integer countryId);
}
