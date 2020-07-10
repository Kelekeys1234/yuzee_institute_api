package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseEnglishEligibility;

public interface CourseEnglishEligibilityDao {
	
	public void save(CourseEnglishEligibility obj);
	
	public void update(CourseEnglishEligibility obj);
	
	public CourseEnglishEligibility get(String id);
	
	public List<CourseEnglishEligibility> getAll();	
	
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(String courseID);

}
