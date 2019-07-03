package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.InstituteDetails;

public interface IInstituteDetailsService {
	
	void save(InstituteDetails obj);
	void update(InstituteDetails obj);
	InstituteDetails get(BigInteger id);
	List<InstituteDetails> getAllInstituteByCountry(BigInteger countryId);
	List<InstituteDetails> getAll();
}
