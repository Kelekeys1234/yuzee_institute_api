package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.UserWatchArticle;
import com.seeka.app.bean.UserWatchCourse;

public interface UserRecommendationDao {

	void save(UserWatchCourse userWatchCourse);

	UserWatchCourse getUserWatchCourseByUserIdAndCourseId(BigInteger userId, BigInteger courseId);

	List<UserWatchCourse> getUserWatchCourse(BigInteger userId);

	void save(UserWatchArticle userWatchArticle);

	UserWatchArticle getUserWatchArticleByUserIdAndArticleId(BigInteger userId, BigInteger articleId);

	List<UserWatchArticle> getUserWatchArticle(BigInteger userId);

	List<Course> getRecommendCourse(BigInteger facultyId, BigInteger instituteId, BigInteger countryId, BigInteger cityId, Double price, Double variablePrice,
			int pageSize, List<BigInteger> courseIds);

	List<Course> getRelatedCourse(BigInteger facultyId, BigInteger instituteId, BigInteger countryId, BigInteger cityId, Double price, Double variablePrice,
			int pageSize, List<BigInteger> courseIds, String courseName);

	List<BigInteger> getOtherUserWatchCourse(BigInteger userId);

	List<BigInteger> getUserWatchCourseIds(BigInteger userId);
	
	List<BigInteger> getOtherUserWatchCourseIds(final BigInteger userId);

}
