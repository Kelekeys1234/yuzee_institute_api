package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseInstitute;
import com.yuzee.app.exception.ValidationException;

public interface CourseInstituteDao {
	List<CourseInstitute> saveAll(List<CourseInstitute> courseInstitutes) throws ValidationException;

	List<CourseInstitute> findLinkedInstitutes(String courseId);

	CourseInstitute findByDestinationCourseId(String destinationCourseId);

	void deleteAll(List<CourseInstitute> courseInstitutes);
}