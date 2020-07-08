package com.seeka.app.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.dto.UserAverageReviewResponseDto;
import com.seeka.app.dto.UserReviewResponseDto;
import com.seeka.app.dto.UserReviewResultDto;
import com.seeka.app.exception.ReviewInvokeException;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ReviewHandler {
	
	private static final String GET_USER_AVERRAGE_REVIEW = "/averageList?entityId={entityId}&entityType={entityType}";
	private static final String GET_USER_REVIEW = "/pageNumber/{pageNumber}/pageSize/{pageSize}?entityId={entityId}&entityType={entityType}";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Map<String, Double> getUserAverageReviewBasedOnDataList(String entityType, List<String> entityIds) throws Exception{
		ResponseEntity<UserAverageReviewResponseDto> userAverageReviewResponseDto = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_AVERRAGE_REVIEW);
			
			for(String entityId : entityIds) {
				params.put("entityId", entityId);
				params.put("entityType", entityType);
				userAverageReviewResponseDto = restTemplate.exchange(path.toString(), HttpMethod.GET, null, UserAverageReviewResponseDto.class, params);
				if (userAverageReviewResponseDto.getStatusCode().value() != 200) {
						throw new ReviewInvokeException ("Error response recieved from review service with error code " + userAverageReviewResponseDto.getStatusCode().value());
				}
			}
		} catch (Exception e) {
			if (e instanceof ReviewInvokeException) {
				throw e;
			} else {
				throw new ReviewInvokeException("Error invoking review service");
			}	
		}
		return userAverageReviewResponseDto.getBody().getData();
	}

	@SuppressWarnings("unchecked")
	public List<UserReviewResultDto> getUserReviewBasedOnData(String entityId, String entityType, Integer pageNumber,
			Integer pageSize, String sortByType, String searchKeyword) throws Exception {
		List<UserReviewResultDto> userReviewList = new ArrayList<>();
		ResponseEntity<UserReviewResponseDto> userReviewResponseDto = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			StringBuilder path = new StringBuilder();
			params.put("entityId", entityId);
			params.put("entityType", entityType);
			params.put("pageNumber", pageNumber.toString());
			params.put("pageSize", pageSize.toString());
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_REVIEW);
			userReviewResponseDto = restTemplate.exchange(path.toString(), HttpMethod.GET, null, UserReviewResponseDto.class, params);
			if (userReviewResponseDto.getStatusCode().value() != 200) {
				throw new ReviewInvokeException ("Error response recieved from review service with error code " + userReviewResponseDto.getStatusCode().value());
			}
			if(userReviewResponseDto.getBody().getData().containsKey("userReviewList")) {
				userReviewList = (List<UserReviewResultDto>) userReviewResponseDto.getBody().getData().get("userReviewList");	
			}
		} catch( Exception e) {
			if (e instanceof ReviewInvokeException) {
				throw e;
			} else {
				throw new ReviewInvokeException("Error invoking review service");
			}	
		}
		return userReviewList;
	}
}
