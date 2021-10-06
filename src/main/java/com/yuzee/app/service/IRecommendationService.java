package com.yuzee.app.service;

import java.util.List;
import java.util.Set;

import com.yuzee.app.bean.Course;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.MyHistoryDto;
import com.yuzee.common.lib.dto.institute.ScholarshipDto;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

public interface IRecommendationService {

	List<InstituteResponseDto> getRecommendedInstitutes(String userId, /* Long startIndex, Long pageSize, Long pageNumber, */ String language)
			throws ValidationException, NotFoundException;

	void getOtherPeopleSearch();

	List<CourseResponseDto> getRecommendedCourses(String userId) throws ValidationException;

	List<Course> getTopSearchedCoursesForFaculty(String facultyId, String userId);

	Set<Course> displayRelatedCourseAsPerUserPastSearch(String userId) throws ValidationException;

	List<InstituteResponseDto> getinstitutesBasedOnOtherPeopleSearch(String userId);

	List<ScholarshipDto> getRecommendedScholarships(String userId, String language) throws ValidationException, NotFoundException;

	List<MyHistoryDto> getRecommendedMyHistory(String userId);
}
