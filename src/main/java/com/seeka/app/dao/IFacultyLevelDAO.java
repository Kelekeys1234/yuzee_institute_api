package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.FacultyLevel;

public interface IFacultyLevelDAO {

	void save(FacultyLevel obj);

	void update(FacultyLevel obj);

	FacultyLevel get(BigInteger id);

	List<FacultyLevel> getAll();

	List<FacultyLevel> getFacultyByCountryIdAndCourseTypeId(BigInteger countryID, BigInteger courseTypeId);

	List<FacultyLevel> getAllFacultyLevelByInstituteId(BigInteger instituteId);

	void deleteFacultyLevel(BigInteger instituteId);
}
