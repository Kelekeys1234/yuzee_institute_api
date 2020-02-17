package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.CityDetails;

public interface ICityDetailsService {
	public List<CityDetails> getAll();
	public CityDetails get(String id);
	public List<CityDetails> getAllCitiesByCountry(String countryId);
}
