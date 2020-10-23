package com.yuzee.app.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.app.dto.QuestionReviewWrapperDto;
import com.yuzee.app.dto.ReviewStarDto;
import com.yuzee.app.dto.ReviewStarWrapperDto;
import com.yuzee.app.dto.QuestionReviewDto;
import com.yuzee.app.dto.UserReviewResponseDto;
import com.yuzee.app.dto.UserReviewResultDto;
import com.yuzee.app.exception.ReviewInvokeException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewHandler {

	private static final String GET_USER_AVERAGE_REVIEW = "/entityType/{entityType}/average";

	private static final String GET_USER_REVIEW_RATING_AVERAGE = "/averageList";

	private static final String GET_USER_REVIEW = "/pageNumber/{pageNumber}/pageSize/{pageSize}?entityId={entityId}&entityType={entityType}";

	private static final String INVOKE_EXCEPTION = "Error invoking review service {}";

	private static final String INVALID_STATUS_CODE_EXCEPTION = "Error response recieved from review service with error code {}";

	@Autowired 
	private RestTemplate restTemplate;

	// api for calculating user review average
	public Map<String, Double> getAverageReview(String entityType, List<String> entityIds)
			throws Exception {
		ResponseEntity<ReviewStarWrapperDto> userAverageReviewResponseDto = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_AVERAGE_REVIEW);
			Map<String, String> urlParams = new HashMap<>();
			urlParams.put("entityType", entityType);
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			entityIds.stream().forEach(e -> uriBuilder.queryParam("entityIds", e));

			userAverageReviewResponseDto = restTemplate.exchange(uriBuilder.buildAndExpand(urlParams).toUri(), HttpMethod.GET, null,
					ReviewStarWrapperDto.class);

			if (userAverageReviewResponseDto.getStatusCode().value() != 200) {
				log.error(INVALID_STATUS_CODE_EXCEPTION, userAverageReviewResponseDto.getStatusCode().value());
				throw new ReviewInvokeException("Error response recieved from review service with error code "
						+ userAverageReviewResponseDto.getStatusCode().value());
			}
		} catch (Exception e) {
			log.error(INVOKE_EXCEPTION, e.getMessage());
			if (e instanceof ReviewInvokeException) {
				throw e;
			} else {
				throw new ReviewInvokeException("Error invoking review service");
			}
		}
		Map<String, Double> map = new HashMap<>();

		List<ReviewStarDto> dtos = userAverageReviewResponseDto.getBody().getData();
		if (!CollectionUtils.isEmpty(dtos)) {
			map = dtos.stream()
					.collect(Collectors.toMap(ReviewStarDto::getEntityId, ReviewStarDto::getReviewStars));
		}
		return map;
	}

	// api for calculating user review rating average
	public Map<String,Double> getUserAverageReviewBasedOnDataList(String entityType, List<String> entityIds)
			throws Exception {
		ResponseEntity<QuestionReviewWrapperDto> userAverageReviewResponseDto = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_REVIEW_RATING_AVERAGE);

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			entityIds.stream().forEach(e -> uriBuilder.queryParam("entityIds", e));
			uriBuilder.queryParam("entityType", entityType);

			userAverageReviewResponseDto = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
					QuestionReviewWrapperDto.class);
			
			if (userAverageReviewResponseDto.getStatusCode().value() != 200) {
				log.error(INVALID_STATUS_CODE_EXCEPTION, userAverageReviewResponseDto.getStatusCode().value());
				throw new ReviewInvokeException("Error response recieved from review service with error code "
						+ userAverageReviewResponseDto.getStatusCode().value());
			}

		} catch (Exception e) {
			log.error(INVOKE_EXCEPTION, e.getMessage());
			if (e instanceof ReviewInvokeException) {
				throw e;
			} else {
				throw new ReviewInvokeException("Error invoking review service");
			}
		}
		Map<String, Double> map = new HashMap<>(); 
		
		List<QuestionReviewDto> dtos = userAverageReviewResponseDto.getBody().getData();
		if (!CollectionUtils.isEmpty(dtos)) {
			map = dtos.stream().collect(Collectors.toMap(QuestionReviewDto::getReviewQuestionId, QuestionReviewDto::getRating));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public List<UserReviewResultDto> getUserReviewBasedOnData(String entityId, String entityType, Integer pageNumber,
			Integer pageSize, String sortByType, String searchKeyword) throws Exception {
		List<UserReviewResultDto> userReviewList = new ArrayList<>();
		ResponseEntity<UserReviewResponseDto> userReviewResponseDto = null;
		Map<String, String> params = new HashMap<>();
		try {
			StringBuilder path = new StringBuilder();
			params.put("entityId", entityId);
			params.put("entityType", entityType);
			params.put("pageNumber", pageNumber.toString());
			params.put("pageSize", pageSize.toString());
			path.append(IConstant.REVIEW_CONNECTION_URL).append(GET_USER_REVIEW);
			userReviewResponseDto = restTemplate.exchange(path.toString(), HttpMethod.GET, null,
					UserReviewResponseDto.class, params);
			if (userReviewResponseDto.getStatusCode().value() != 200) {
				log.error(INVALID_STATUS_CODE_EXCEPTION, userReviewResponseDto.getStatusCode().value());
				throw new ReviewInvokeException("Error response recieved from review service with error code "
						+ userReviewResponseDto.getStatusCode().value());
			}
			if (userReviewResponseDto.getBody().getData().containsKey("userReviewList")) {
				userReviewList = (List<UserReviewResultDto>) userReviewResponseDto.getBody().getData()
						.get("userReviewList");
			}
		} catch (Exception e) {
			log.error(INVOKE_EXCEPTION, e.getMessage());
			if (e instanceof ReviewInvokeException) {
				throw e;
			} else {
				throw new ReviewInvokeException("Error invoking review service");
			}
		}
		return userReviewList;
	}
}
