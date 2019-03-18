package com.seeka.freshfuture.app.dao;

import java.util.List;

import com.seeka.freshfuture.app.bean.CourseType;

public interface ICourseTypeDAO {
	
	public void save(CourseType obj);
	public void update(CourseType obj);
	public CourseType get(Integer id);
	public List<CourseType> getAll();
	public List<CourseType> getCourseTypeByCountryId(Integer countryID);
}
