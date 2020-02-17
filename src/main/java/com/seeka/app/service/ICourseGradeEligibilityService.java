package com.seeka.app.service;import java.util.List;

import com.seeka.app.bean.CourseGradeEligibility;

public interface ICourseGradeEligibilityService {
	
	public void save(CourseGradeEligibility obj);
	public void update(CourseGradeEligibility obj);
	public CourseGradeEligibility get(String id);
	public List<CourseGradeEligibility> getAll();
	public CourseGradeEligibility getAllEnglishEligibilityByCourse(String courseID);
}
