package com.yuzee.app.service;

import java.util.List;

import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;

public interface ITop10CourseService {

	void deleteAllTop10Courses();

	List<String> getAllDistinctFaculty();

	List<String> getTop10CourseKeyword(String facultyId);

	List<CourseResponseDto> getTop10RandomCoursesForGlobalSearchLandingPage() throws ValidationException, NotFoundException, InvokeException;
}
