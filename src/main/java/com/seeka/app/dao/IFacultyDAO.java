package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Faculty;

public interface IFacultyDAO {
	
	public void save(Faculty obj);
	public void update(Faculty obj);
	public Faculty get(Integer id);
	public List<Faculty> getAll();
	public List<Faculty> getFacultyByCountryIdAndLevelId(Integer countryID,Integer levelId);
}
