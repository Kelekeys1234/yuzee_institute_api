package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import com.seeka.app.bean.CourseEnglishEligibility;

public interface ICourseEnglishEligibilityService {
	
	public void save(CourseEnglishEligibility obj);
	public void update(CourseEnglishEligibility obj);
	public CourseEnglishEligibility get(UUID id);
	public List<CourseEnglishEligibility> getAll(); 
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(UUID courseID);
}
