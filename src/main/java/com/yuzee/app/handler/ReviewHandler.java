package com.yuzee.app.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.UserAverageReviewResponseDto;
import com.yuzee.app.dto.UserReviewResponseDto;
import com.yuzee.app.dto.UserReviewResultDto;
import com.yuzee.app.exception.ReviewInvokeException;
import com.yuzee.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ReviewHandler {
	
	private static final String GET_USER_REVIEW_AVERAGE = "/entityType/{entityType}/average?entityId={entityId}";
	private static final String GET_USER_REVIEW_RATING_AVERAGE = "/averageList?entityId={entityId}&entityType={entityType}";
	private static final String GET_USER_REVIEW = "/pageNumber/{pageNumber}/pageSize/{pageSize}?entityId={entityId}&entityType={entityType}";
	
	@Autowired
	private RestTemplate restTemplate;
	
	// api for calculating user review average
	public Map<String, Double> getUserReviewAverageBasedOnEntityType(String entityType, List<String> entityIds) throws Exception{
		ResponseEntity<UserAverageReviewResponseDto> userAverageReviewResponseDto = null;
		Map<String, String> params = new HashMap<>();
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_REVIEW_AVERAGE);

			params.put("entityType", entityType);
			for(String entityId : entityIds) {
				params.put("entityId", entityId);
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

	// api for calculating user review rating average
	public Map<String, Double> getUserAverageReviewBasedOnDataList(String entityType, List<String> entityIds) throws Exception{
		ResponseEntity<UserAverageReviewResponseDto> userAverageReviewResponseDto = null;
		Map<String, String> params = new HashMap<>();
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_REVIEW_RATING_AVERAGE);
			
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
