package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserReview;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserReviewDao {

	void save(UserReview userReview);

	List<UserReview> getUserReview(BigInteger userId, BigInteger entityId, String entityType);

	UserReview getUserAverageReview(BigInteger entityId, String entityType);

}
