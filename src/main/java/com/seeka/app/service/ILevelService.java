package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.Level;

public interface ILevelService {
	
	public void save(Level obj);
	public void update(Level obj);
	public Level get(BigInteger id);
	public List<Level> getAll();
	public List<Level> getCourseTypeByCountryId(BigInteger countryID);
	public List<Level> getLevelByCountryId(BigInteger countryId);
	public List<Level> getAllLevelByCountry();
}
