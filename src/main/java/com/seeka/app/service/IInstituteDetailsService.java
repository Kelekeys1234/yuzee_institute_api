package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.InstituteDetails;

public interface IInstituteDetailsService {
	
	public void save(InstituteDetails obj);
	public void update(InstituteDetails obj);
	public InstituteDetails get(BigInteger id);
	public List<InstituteDetails> getAllInstituteByCountry(BigInteger countryId);
	public List<InstituteDetails> getAll();
}
