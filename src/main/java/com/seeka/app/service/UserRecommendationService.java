package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.UserArticleRequestDto;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;

public interface UserRecommendationService {

	void createUserWatchCourse(UserCourseRequestDto courseRequestDto) throws ValidationException;

	List<UserWatchCourse> getUserWatchCourse(BigInteger userId);

	List<UserWatchArticle> getUserWatchArticle(BigInteger userId);

	void createUserWatchArticle(UserArticleRequestDto userArticleRequestDto) throws ValidationException;

	List<Course> getRecommendCourse(BigInteger courseId, BigInteger userId) throws ValidationException;

	List<Course> getRelatedCourse(BigInteger courseId) throws ValidationException;

	List<CourseResponseDto> getCourseRelated(BigInteger courseId) throws ValidationException;

	List<CourseResponseDto> getCourseRecommended(BigInteger courseId) throws ValidationException;
}
