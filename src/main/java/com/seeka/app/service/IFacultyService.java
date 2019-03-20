package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Faculty;

public interface IFacultyService {
	
	public void save(Faculty obj);
	public void update(Faculty obj);
	public Faculty get(Integer id);
	public List<Faculty> getAll();
	public List<Faculty> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId);
}
