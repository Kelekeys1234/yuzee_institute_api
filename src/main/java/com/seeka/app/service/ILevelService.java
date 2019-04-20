package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.Level;

public interface ILevelService {
	
	public void save(Level obj);
	public void update(Level obj);
	public Level get(UUID id);
	public List<Level> getAll();
	public List<Level> getCourseTypeByCountryId(UUID countryID);
	public List<Level> getLevelByCountryId(UUID countryId);
	public List<Level> getAllLevelByCountry();
}
