package com.seeka.app.dao;import java.util.List;

import com.seeka.app.bean.CityDetails;

public interface ICityDetailsDAO {
	public List<CityDetails> getAll();
	public CityDetails get(String id);
	public List<CityDetails> getAllCitiesByCountry(String countryId);
}
