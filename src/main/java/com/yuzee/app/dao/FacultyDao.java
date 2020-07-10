package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dto.FacultyDto;

public interface FacultyDao {

	public void save(final Faculty obj);

	public void update(final Faculty obj);

	public Faculty get(final String id);

	public List<Faculty> getAll();

	public List<Faculty> getFacultyByCountryIdAndLevelId(final String countryID, final String levelId);

	public List<FacultyDto> getAllFacultyByCountryIdAndLevel();

	public List<FacultyDto> getFacultyByInstituteId(final String instituteId);

	public List<FacultyDto> getFacultyByListOfInstituteId(final String instituteId);

	public List<Faculty> getFacultyListByFacultyNames(final List<String> facultyNameList);

	public List<FacultyDto> getCourseFaculty(final String countryId, final String levelId);

	public Faculty getFacultyByFacultyName(String facultyName);

	public List<String> getAllFacultyNamesByInstituteId(final String instituteId);

}
