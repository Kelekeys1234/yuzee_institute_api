package com.seeka.app.dao;import java.math.BigInteger;

import java.util.List;


import com.seeka.app.bean.CourseEnglishEligibility;

public interface ICourseEnglishEligibilityDAO {
	
	public void save(CourseEnglishEligibility obj);
	public void update(CourseEnglishEligibility obj);
	public CourseEnglishEligibility get(BigInteger id);
	public List<CourseEnglishEligibility> getAll();	
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(BigInteger courseID);
}
