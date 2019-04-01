package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.FacultyLevel;

public interface IFacultyLevelDAO {
	
	public void save(FacultyLevel obj);
	public void update(FacultyLevel obj);
	public FacultyLevel get(Integer id);
	public List<FacultyLevel> getAll();
	public List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(Integer countryID,Integer courseTypeId);
	public List<FacultyLevel> getAllFacultyLevelByInstituteId(Integer instituteId);
}
