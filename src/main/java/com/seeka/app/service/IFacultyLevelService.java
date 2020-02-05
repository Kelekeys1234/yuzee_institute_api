package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.FacultyLevel;

public interface IFacultyLevelService {

	void save(FacultyLevel obj);

	void update(FacultyLevel obj);

	FacultyLevel get(BigInteger id);

	List<FacultyLevel> getAll();

	List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(BigInteger countryID, BigInteger courseTypeId);

	List<FacultyLevel> getAllFacultyLevelByInstituteId(String instituteId);

	void deleteFacultyLevel(String instituteId);
}
