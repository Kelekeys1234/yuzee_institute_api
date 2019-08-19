package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;

public interface UserRecommendationDao {

	void save(UserWatchCourse userWatchCourse);

	UserWatchCourse getUserWatchCourseByUserIdAndCourseId(BigInteger userId, BigInteger courseId);

	List<UserWatchCourse> getRecommendCourse(BigInteger userId);

	void save(UserWatchArticle existingUserRecommendArticle);

	UserWatchArticle getUserWatchArticleByUserIdAndArticleId(BigInteger userId, BigInteger articleId);

	List<UserWatchArticle> getRecommendArticle(BigInteger userId);

}
