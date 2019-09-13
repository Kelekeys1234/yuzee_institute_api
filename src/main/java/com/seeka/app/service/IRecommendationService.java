package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Course;
import com.seeka.app.exception.ValidationException;

public interface IRecommendationService {

	void getRecommendedInstitutes();
	
	void getOtherPeopleSearch();

	List<Course> getRecommendedCourses(BigInteger userId) throws ValidationException;
	
	List<Course> getTopSearchedCoursesForFaculty(BigInteger facultyId, BigInteger userId);
}
