package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.Top10Course;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.exception.ValidationException;

public interface ITop10CourseService {

	void saveTop10Courses(Top10Course top10Course);

	void deleteAllTop10Courses();

	List<String> getAllDistinctFaculty();

	List<String> getTop10CourseKeyword(String facultyId);

	List<CourseResponseDto> getTop10RandomCoursesForGlobalSearchLandingPage() throws ValidationException;
}
