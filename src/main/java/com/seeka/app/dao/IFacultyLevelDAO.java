package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;

import com.seeka.app.bean.FacultyLevel;

public interface IFacultyLevelDAO {
	
	public void save(FacultyLevel obj);
	public void update(FacultyLevel obj);
	public FacultyLevel get(BigInteger id);
	public List<FacultyLevel> getAll();
	public List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(BigInteger countryID,BigInteger courseTypeId);
	public List<FacultyLevel> getAllFacultyLevelByInstituteId(BigInteger instituteId);
}
