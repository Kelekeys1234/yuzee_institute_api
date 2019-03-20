package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.Level;

public interface ILevelService {
	
	public void save(Level obj);
	public void update(Level obj);
	public Level get(Integer id);
	public List<Level> getAll();
	public List<Level> getCourseTypeByCountryId(Integer countryID);
}
