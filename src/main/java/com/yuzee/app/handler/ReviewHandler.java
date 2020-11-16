package com.yuzee.app.handler;

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

import com.yuzee.app.dto.ReviewStarDto;
import com.yuzee.app.dto.ReviewStarWrapperDto;
import com.yuzee.app.exception.ReviewInvokeException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewHandler {

	private static final String GET_USER_AVERAGE_REVIEW = "/user/review/average/entityType/{entityType}";

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
}
