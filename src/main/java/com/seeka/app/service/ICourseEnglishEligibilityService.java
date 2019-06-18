package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CourseEnglishEligibility;

public interface ICourseEnglishEligibilityService {
	
	public void save(CourseEnglishEligibility obj);
	public void update(CourseEnglishEligibility obj);
	public CourseEnglishEligibility get(BigInteger id);
	public List<CourseEnglishEligibility> getAll(); 
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(BigInteger courseID);
}
