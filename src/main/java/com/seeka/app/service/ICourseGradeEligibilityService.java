package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.CourseGradeEligibility;

public interface ICourseGradeEligibilityService {
	
	public void save(CourseGradeEligibility obj);
	public void update(CourseGradeEligibility obj);
	public CourseGradeEligibility get(Integer id);
	public List<CourseGradeEligibility> getAll(); 
}
