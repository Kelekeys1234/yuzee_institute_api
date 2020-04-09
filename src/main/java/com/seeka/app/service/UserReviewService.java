package com.seeka.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.util.IConstant;

/**
 *
 * @author SeekADegree
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserReviewService implements IUserReviewService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Map<String, Double> getUserAverageReviewBasedOnDataList(String entityType, List<String> entityIds) {
		Map<String, Double> finalResponse = new HashMap<>();
		for(String entityId : entityIds) {
			ResponseEntity<Map> result = restTemplate.getForEntity(IConstant.REVIEW_CONNECTION_URL
					+ "/review/user/averageList?entityType="+entityType+"&entityId="+entityId, Map.class);
			Map<String, Object> responseMap = (Map<String, Object>) result.getBody();
			Map<String, Object> bodyResponseMap = (Map<String, Object>) responseMap.get("body");
			finalResponse = (Map<String, Double>) bodyResponseMap.get("data");
		}
		return finalResponse;
	}

	@Override
	public List<UserReviewResultDto> getUserReviewBasedOnData(String entityId, String entityType, Integer pageNumber,
			Integer pageSize, String sortByType, String searchKeyword) {
		ResponseEntity<Map> result = restTemplate.getForEntity(IConstant.REVIEW_CONNECTION_URL + 
				"/review/user/pageNumber/0/pageSize/5?entityId="+entityId+"&entityType="+entityType, Map.class);
		Map<String, Object> responseMap = (Map<String, Object>) result.getBody();
		Map<String, Object> bodyResponseMap = (Map<String, Object>) responseMap.get("body");
		List<UserReviewResultDto> userReviewDto = (List<UserReviewResultDto>) bodyResponseMap.get("data");
		return userReviewDto;
	}
}
