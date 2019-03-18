package com.seeka.freshfuture.app.dao;

import java.util.List;

import com.seeka.freshfuture.app.bean.Faculty;

public interface IFacultyDAO {
	
	public void save(Faculty obj);
	public void update(Faculty obj);
	public Faculty get(Integer id);
	public List<Faculty> getAll();
	public List<Faculty> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId);
}
