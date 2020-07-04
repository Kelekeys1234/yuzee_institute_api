package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Faculty;
import com.seeka.app.dto.FacultyDto;

public interface FacultyDao {

	public void save(Faculty obj);

	public void update(Faculty obj);

	public Faculty get(String id);

	public List<Faculty> getAll();

	public List<Faculty> getFacultyByCountryIdAndLevelId(String countryID, String levelId);

	public List<FacultyDto> getAllFacultyByCountryIdAndLevel();

	public List<FacultyDto> getFacultyByInstituteId(String instituteId);

	public List<FacultyDto> getFacultyByListOfInstituteId(String instituteId);

	public List<FacultyDto> getCourseFaculty(String countryId, String levelId);

	public List<Faculty> getFacultyListByFacultyNames(List<String> facultyNameList);

	public Faculty getFacultyByFacultyName(String facultyName);

	public List<String> getAllFacultyNamesByInstituteId(String instituteId);

}
