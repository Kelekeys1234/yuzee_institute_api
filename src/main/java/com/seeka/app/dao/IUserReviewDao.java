package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserReview;
import com.seeka.app.bean.UserReviewRating;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserReviewDao {

	void save(UserReview userReview);

	List<UserReview> getUserReviewList(BigInteger userId, BigInteger entityId, String entityType, Integer startIndex, Integer pageSize, String sortByType,
			String searchKeyword);

	List<Object> getUserAverageReview(BigInteger entityId, String entityType);

	UserReview getUserReview(BigInteger userReviewId);

	void saveUserReviewRating(UserReviewRating userReviewRating);

	List<UserReviewRating> getUserReviewRatings(BigInteger userReviewId);

	Double getReviewStar(BigInteger entityId, String entityType);

	int getUserReviewCount(BigInteger userId, BigInteger entityId, String entityType, String searchKeyword);
}
