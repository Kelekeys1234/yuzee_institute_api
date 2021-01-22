package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.exception.ValidationException;

public interface CourseEnglishEligibilityDao {

	List<CourseEnglishEligibility> saveAll(List<CourseEnglishEligibility> courseEnglishEligibilities)
			throws ValidationException;

	List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(String courseID);

	void deleteByCourseIdAndIdIn(String courseId, List<String> ids);

	List<CourseEnglishEligibility> findByCourseIdAndIdIn(String courseId, List<String> ids);

}
