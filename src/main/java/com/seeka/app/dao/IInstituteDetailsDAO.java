package com.seeka.app.dao;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.InstituteDetails;

public interface IInstituteDetailsDAO {
	
	public void save(InstituteDetails obj);
	public void update(InstituteDetails obj);
	public InstituteDetails get(UUID id);
	public List<InstituteDetails> getAllInstituteByCountry(UUID countryId);	
	public List<InstituteDetails> getAll();	
}
