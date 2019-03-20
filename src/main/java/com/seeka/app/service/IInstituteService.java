package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Institute;

public interface IInstituteService {
	
	public void save(Institute obj);
	public void update(Institute obj);
	public Institute get(Integer id);
	public List<Institute> getAllInstituteByCountry(Integer countryId);
	public List<Institute> getAll();
}
