package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.CourseDetails;

public interface ICourseDetailsService {
	
	public void save(CourseDetails obj);
	public void update(CourseDetails obj);
	public CourseDetails get(Integer id);
	public List<CourseDetails> getAll(); 
}
