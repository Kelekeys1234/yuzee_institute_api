package com.seeka.freshfuture.app.dao;

import java.util.List;

import com.seeka.freshfuture.app.bean.InstituteServiceDetails;

public interface IInstituteServiceDetailsDAO {
	
	public void save(InstituteServiceDetails obj);
	public void update(InstituteServiceDetails obj);
	public InstituteServiceDetails get(Integer id);
	public List<InstituteServiceDetails> getAllInstituteByCountry(Integer countryId);	
	public List<InstituteServiceDetails> getAll();	
}
