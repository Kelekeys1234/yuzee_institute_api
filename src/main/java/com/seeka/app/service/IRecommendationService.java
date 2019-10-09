package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.seeka.app.bean.Course;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

public interface IRecommendationService {

	List<InstituteResponseDto> getRecommendedInstitutes(BigInteger userId, Long startIndex, Long pageSize, Long pageNumber, String language) throws ValidationException, NotFoundException;
	
	void getOtherPeopleSearch();

	List<CourseResponseDto> getRecommendedCourses(BigInteger userId) throws ValidationException;
	
	List<Course> getTopSearchedCoursesForFaculty(BigInteger facultyId, BigInteger userId);
	
	Set<Course> displayRelatedCourseAsPerUserPastSearch(BigInteger userId) throws ValidationException;

	List<InstituteResponseDto> getinstitutesBasedOnOtherPeopleSearch(BigInteger userId);
}
