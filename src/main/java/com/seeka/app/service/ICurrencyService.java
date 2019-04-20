package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.Currency;

public interface ICurrencyService {
	
	public void save(Currency obj);
	public void update(Currency obj);
	public Currency get(UUID id);
	public List<Currency> getAll();
	public List<Currency> getCourseTypeByCountryId(UUID countryID);
	public List<Currency> getCurrencyByCountryId(UUID countryId);
}
