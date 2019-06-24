package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CityDetails;

public interface ICityDetailsDAO {
	public List<CityDetails> getAll();
	public CityDetails get(BigInteger id);
	public List<CityDetails> getAllCitiesByCountry(BigInteger countryId);
}
