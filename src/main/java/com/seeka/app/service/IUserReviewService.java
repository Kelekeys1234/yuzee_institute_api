package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserReview;
import com.seeka.app.dto.UserReviewDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.exception.ValidationException;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserReviewService {

	UserReview addUserReview(UserReviewDto userReviewDto) throws ValidationException;

	List<UserReviewDto> getUserReviewList(BigInteger userId, Integer pageNumber, Integer pageSize);

	UserReviewResultDto getUserReviewDetails(BigInteger userId) throws ValidationException;

	List<UserReviewDto> getUserReviewBasedOnData(BigInteger entityId, String entityType, Integer pageNumber, Integer pageSize) throws ValidationException;

	UserReviewResultDto getUserAverageReviewBasedOnData(BigInteger entityId, String entityType) throws ValidationException;

	void deleteUserReview(BigInteger userReviewId) throws ValidationException;

}
