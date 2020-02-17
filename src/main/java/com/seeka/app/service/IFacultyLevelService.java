package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.FacultyLevel;

public interface IFacultyLevelService {

	void save(FacultyLevel obj);

	void update(FacultyLevel obj);

	FacultyLevel get(String id);

	List<FacultyLevel> getAll();

	List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(String countryID, String courseTypeId);

	List<FacultyLevel> getAllFacultyLevelByInstituteId(String instituteId);

	void deleteFacultyLevel(String instituteId);
}
