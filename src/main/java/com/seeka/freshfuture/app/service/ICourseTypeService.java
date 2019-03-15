package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.CourseType;

public interface ICourseTypeService {
	
	public void save(CourseType obj);
	public void update(CourseType obj);
	public CourseType get(Integer id);
	public List<CourseType> getAll();
}
