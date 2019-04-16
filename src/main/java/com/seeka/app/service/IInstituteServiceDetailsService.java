package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.InstituteServiceDetails;

public interface IInstituteServiceDetailsService {
	
	public void save(InstituteServiceDetails obj);
	public void update(InstituteServiceDetails obj);
	public InstituteServiceDetails get(Integer id);
	public List<InstituteServiceDetails> getAll();
	public List<String> getAllServices(Integer instituteId);
}
