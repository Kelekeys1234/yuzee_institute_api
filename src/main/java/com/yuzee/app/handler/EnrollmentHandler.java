package com.yuzee.app.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.EnrollmentWrapperDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.util.IConstant;

@Service
public class EnrollmentHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentHandler.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GET_ENROLLMENT_COUNT = "/enrollment/count/courseId/{courseId}";
	
	public Integer getTotalCountOfEnrollment(String userId, String courseId) throws InvokeException {
		ResponseEntity<EnrollmentWrapperDto> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("courseId", courseId);
			HttpHeaders headers = new HttpHeaders();
			headers.add("userId", userId);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			StringBuilder path = new StringBuilder();
			path.append(IConstant.APPILICATION_URL).append(GET_ENROLLMENT_COUNT);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, EnrollmentWrapperDto.class, params);
			if(responseEntity.getStatusCode().value() != 200) {
				throw new InvokeException ("Error response recieved from Application service with error code " 
						+ responseEntity.getStatusCode().value());
			}
		} catch(Exception e) {
			LOGGER.error("Error invoking common service {}", e);
			if (e instanceof InvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking Application service");
			}	
		}
		return responseEntity.getBody().getData().getEnrollmentCount();
	}
}
