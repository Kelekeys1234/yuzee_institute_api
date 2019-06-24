package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CityDetails;

public interface ICityDetailsService {
	public List<CityDetails> getAll();
	public CityDetails get(BigInteger id);
	public List<CityDetails> getAllCitiesByCountry(BigInteger countryId);
}
