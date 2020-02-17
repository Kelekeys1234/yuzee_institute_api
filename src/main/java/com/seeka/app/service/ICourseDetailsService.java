package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.CourseDetails;

public interface ICourseDetailsService {
	
	public void save(CourseDetails obj);
	public void update(CourseDetails obj);
	public CourseDetails get(String id);
	public List<CourseDetails> getAll(); 
}
