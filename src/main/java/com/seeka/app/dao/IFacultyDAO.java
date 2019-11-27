package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Faculty;

public interface IFacultyDAO {

	void save(Faculty obj);

	void update(Faculty obj);

	Faculty get(BigInteger id);

	List<Faculty> getAll();

	List<Faculty> getFacultyByCountryIdAndLevelId(BigInteger countryID, BigInteger levelId);

	List<Faculty> getAllFacultyByCountryIdAndLevel();

	List<Faculty> getFacultyByInstituteId(BigInteger instituteId);

	List<Faculty> getFacultyByListOfInstituteId(String instituteId);

	List<Faculty> getCourseFaculty(BigInteger countryId, BigInteger levelId);

	List<Faculty> getFacultyListByFacultyNames(List<String> facultyNameList);

}
