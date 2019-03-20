package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.InstituteDetails;

public interface IInstituteDetailsService {
	
	public void save(InstituteDetails obj);
	public void update(InstituteDetails obj);
	public InstituteDetails get(Integer id);
	public List<InstituteDetails> getAllInstituteByCountry(Integer countryId);
	public List<InstituteDetails> getAll();
}
