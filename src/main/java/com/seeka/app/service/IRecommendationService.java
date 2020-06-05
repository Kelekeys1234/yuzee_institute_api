package com.seeka.app.service;

import java.util.List;
import java.util.Set;

import com.seeka.app.bean.Course;
import com.seeka.app.dto.ArticleResposeDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.MyHistoryDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

public interface IRecommendationService {

	List<InstituteResponseDto> getRecommendedInstitutes(String userId, /* Long startIndex, Long pageSize, Long pageNumber, */ String language)
			throws ValidationException, NotFoundException;

	void getOtherPeopleSearch();

	List<CourseResponseDto> getRecommendedCourses(String userId) throws ValidationException;

	List<Course> getTopSearchedCoursesForFaculty(String facultyId, String userId);

	Set<Course> displayRelatedCourseAsPerUserPastSearch(String userId) throws ValidationException;

	List<InstituteResponseDto> getinstitutesBasedOnOtherPeopleSearch(String userId);

	List<ScholarshipDto> getRecommendedScholarships(String userId, String language) throws ValidationException, NotFoundException;

	List<ArticleResposeDto> getRecommendedArticles(String userId) throws ValidationException;
	
	List<MyHistoryDto> getRecommendedMyHistory(String userId);
}
