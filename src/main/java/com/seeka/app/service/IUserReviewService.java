package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import com.seeka.app.dto.UserReviewResultDto;

/**
 *
 * @author SeekADegree
 *
 */
public interface IUserReviewService {
	
	public Map<String, Double> getUserAverageReviewBasedOnDataList(String entityType, List<String> entityIds) throws Exception;
	
	List<UserReviewResultDto> getUserReviewBasedOnData(String entityId, String entityType, 
				Integer pageNumber,Integer pageSize, String sortByType, String searchKeyword) throws Exception ;
}
