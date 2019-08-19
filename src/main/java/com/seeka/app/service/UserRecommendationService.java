package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;
import com.seeka.app.dto.UserArticleRequestDto;
import com.seeka.app.dto.UserCourseRequestDto;
import com.seeka.app.exception.ValidationException;

public interface UserRecommendationService {

	void createRecommendCourse(UserCourseRequestDto courseRequestDto) throws ValidationException;

	List<UserWatchCourse> getRecommendCourse(BigInteger userId);

	List<UserWatchArticle> getRecommendArticle(BigInteger userId);

	void createRecommendArticle(UserArticleRequestDto userArticleRequestDto) throws ValidationException;
}
