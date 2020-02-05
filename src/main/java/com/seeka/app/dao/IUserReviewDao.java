package com.seeka.app.dao;

import java.util.List;
import java.util.Map;

import com.seeka.app.bean.UserReview;
import com.seeka.app.bean.UserReviewRating;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserReviewDao {

	void save(UserReview userReview);

	List<UserReview> getUserReviewList(String userId, String entityId, String entityType, Integer startIndex, Integer pageSize, String sortByType,
			String searchKeyword);

	List<Object> getUserAverageReview(String entityId, String entityType);

	UserReview getUserReview(String userReviewId);

	void saveUserReviewRating(UserReviewRating userReviewRating);

	List<UserReviewRating> getUserReviewRatings(String userReviewId);

	Double getReviewStar(String entityId, String entityType);

	int getUserReviewCount(String userId, String entityId, String entityType, String searchKeyword);

	Map<String, Double> getUserAverageReviewList(List<String> entityIdList, String entityType);
}
