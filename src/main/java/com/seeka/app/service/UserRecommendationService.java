package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Course;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.exception.ValidationException;

public interface UserRecommendationService {

	List<Course> getRecommendCourse(BigInteger courseId, BigInteger userId) throws ValidationException;

	List<Course> getRelatedCourse(BigInteger courseId) throws ValidationException;

	List<CourseResponseDto> getCourseRelated(BigInteger courseId) throws ValidationException;

	List<CourseResponseDto> getCourseRecommended(BigInteger courseId) throws ValidationException;

	List<CourseResponseDto> getCourseNoResultRecommendation(BigInteger facultyId, BigInteger countryId, Integer startIndex, Integer pageSize)
			throws ValidationException;

}
