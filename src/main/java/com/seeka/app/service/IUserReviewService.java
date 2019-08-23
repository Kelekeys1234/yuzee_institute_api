package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserReview;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserReviewService {

	UserReview addUserReview(UserReviewDto userReviewDto) throws ValidationException;

	List<UserReview> getUserReview(BigInteger userId);

	List<UserReview> getUserReviewBasedOnData(BigInteger entityId, String entityType) throws ValidationException;

	UserReview getUserAverageReviewBasedOnData(BigInteger entityId, String entityType);

}
