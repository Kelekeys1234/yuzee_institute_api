package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.CourseDetails;

public interface ICourseDetailsService {
	
	public void save(CourseDetails obj);
	public void update(CourseDetails obj);
	public CourseDetails get(UUID id);
	public List<CourseDetails> getAll(); 
}
