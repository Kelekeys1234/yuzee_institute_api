package com.seeka.app.service;

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

	List<UserReviewResultDto> getUserReviewList(String userId, Integer pageNumber, Integer pageSize) throws ValidationException;

	UserReviewResultDto getUserReviewDetails(String userId) throws ValidationException;

	List<UserReviewResultDto> getUserReviewBasedOnData(String entityId, String entityType, Integer startIndex, Integer pageSize, String sortByType,
			String searchKeyword) throws ValidationException;

	UserReviewResultDto getUserAverageReviewBasedOnData(String entityId, String entityType) throws ValidationException;

	void deleteUserReview(String userReviewId) throws ValidationException;

	List<UserReviewResultDto> getUserReviewList() throws ValidationException;

	int getUserReviewCount(String userId, String entityId, String entityType, String searchKeyword);

	Map<String, Double> getUserAverageReviewBasedOnDataList(List<String> entityIdList, String entityType);

}
