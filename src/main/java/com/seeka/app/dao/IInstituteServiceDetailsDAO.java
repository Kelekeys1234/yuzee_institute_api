package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.InstituteServiceDetails;

public interface IInstituteServiceDetailsDAO {
	
	public void save(InstituteServiceDetails obj);
	public void update(InstituteServiceDetails obj);
	public InstituteServiceDetails get(UUID id);
	public List<InstituteServiceDetails> getAll();	
	public List<String> getAllServices(UUID instituteId);
}
