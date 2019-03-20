package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.FacultyLevel;

public interface IFacultyLevelService {
	
	public void save(FacultyLevel obj);
	public void update(FacultyLevel obj);
	public FacultyLevel get(Integer id);
	public List<FacultyLevel> getAll();
	public List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId);
}
