package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseEnglishEligibility;

public interface CourseEnglishEligibilityDao {
	List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(String courseID);
}
