package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

	List<UserReviewResultDto> getUserReviewList(BigInteger userId, Integer pageNumber, Integer pageSize) throws ValidationException;

	UserReviewResultDto getUserReviewDetails(BigInteger userId) throws ValidationException;

	List<UserReviewResultDto> getUserReviewBasedOnData(BigInteger entityId, String entityType, Integer startIndex, Integer pageSize, String sortByType,
			String searchKeyword) throws ValidationException;

	UserReviewResultDto getUserAverageReviewBasedOnData(BigInteger entityId, String entityType) throws ValidationException;

	void deleteUserReview(BigInteger userReviewId) throws ValidationException;

	List<UserReviewResultDto> getUserReviewList() throws ValidationException;

	int getUserReviewCount(BigInteger userId, BigInteger entityId, String entityType, String searchKeyword);

	Map<BigInteger, Double> getUserAverageReviewBasedOnDataList(List<BigInteger> entityIdList, String entityType);

}
