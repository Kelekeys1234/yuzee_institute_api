package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.InstituteServiceDetails;

public interface IInstituteServiceDetailsService {
	
	public void save(InstituteServiceDetails obj);
	public void update(InstituteServiceDetails obj);
	public InstituteServiceDetails get(UUID id);
	public List<InstituteServiceDetails> getAll();
	public List<String> getAllServices(UUID instituteId);
}
