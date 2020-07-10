package com.yuzee.app.service;

import java.util.List;

import com.yuzee.app.bean.Course;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.exception.ValidationException;

public interface UserRecommendationService {

	List<Course> getRecommendCourse(String courseId, String userId) throws ValidationException;

	List<Course> getRelatedCourse(String courseId) throws ValidationException;

	List<CourseResponseDto> getCourseRelated(String courseId) throws ValidationException;

	List<CourseResponseDto> getCourseRecommended(String courseId) throws ValidationException;

	List<CourseResponseDto> getCourseNoResultRecommendation(String facultyId, String countryId, Integer startIndex, Integer pageSize)
			throws ValidationException;

	List<CourseResponseDto> getCheapestCourse(String facultyId, String countryId, String levelId, String cityId, Integer startIndex,
			Integer pageSize) throws ValidationException;

}
