package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Faculty;

public interface FacultyDao {

	public void saveOrUpdateFaculty(final Faculty obj);

	public Faculty get(final String id);

	public List<Faculty> getAll();

	public List<Faculty> getFacultyListByFacultyNames(final List<String> facultyNameList);

	public Faculty getFacultyByFacultyName(String facultyName);

}
