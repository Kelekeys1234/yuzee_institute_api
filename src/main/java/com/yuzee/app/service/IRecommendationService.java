package com.yuzee.app.service;

import java.util.List;
import java.util.Set;

import com.yuzee.app.bean.Course;
import com.yuzee.app.dto.ArticleResposeDto;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.MyHistoryDto;
import com.yuzee.app.dto.ScholarshipDTO;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

public interface IRecommendationService {

	List<InstituteResponseDto> getRecommendedInstitutes(String userId, /* Long startIndex, Long pageSize, Long pageNumber, */ String language)
			throws ValidationException, NotFoundException;

	void getOtherPeopleSearch();

	List<CourseResponseDto> getRecommendedCourses(String userId) throws ValidationException;

	List<Course> getTopSearchedCoursesForFaculty(String facultyId, String userId);

	Set<Course> displayRelatedCourseAsPerUserPastSearch(String userId) throws ValidationException;

	List<InstituteResponseDto> getinstitutesBasedOnOtherPeopleSearch(String userId);

	List<ScholarshipDTO> getRecommendedScholarships(String userId, String language) throws ValidationException, NotFoundException;

	List<ArticleResposeDto> getRecommendedArticles(String userId) throws ValidationException;
	
	List<MyHistoryDto> getRecommendedMyHistory(String userId);
}
