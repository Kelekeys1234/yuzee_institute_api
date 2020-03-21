package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Faculty;

public interface IFacultyDAO {

	void save(Faculty obj);

	void update(Faculty obj);

	Faculty get(String id);

	List<Faculty> getAll();

	List<Faculty> getFacultyByCountryIdAndLevelId(String countryID, String levelId);

	List<Faculty> getAllFacultyByCountryIdAndLevel();

	List<Faculty> getFacultyByInstituteId(String instituteId);

	List<Faculty> getFacultyByListOfInstituteId(String instituteId);

	List<Faculty> getCourseFaculty(String countryId, String levelId);

	List<Faculty> getFacultyListByFacultyNames(List<String> facultyNameList);
	
	Faculty getFacultyByFacultyName(String facultyName);

	List<String> getAllFacultyNamesByInstituteId(String instituteId);

}
