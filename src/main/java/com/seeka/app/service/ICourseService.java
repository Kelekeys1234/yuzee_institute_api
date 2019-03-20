package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Course;

public interface ICourseService {
	
	public void save(Course obj);
	public void update(Course obj);
	public Course get(Integer id);
	public List<Course> getAll(); 
}
