package com.yuzee.app.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.UserMyCourseDto;
import com.yuzee.app.dto.UserMyCourseWrapperDto;
import com.yuzee.app.dto.UserViewCourseDto;
import com.yuzee.app.dto.UserViewCourseWrapperDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ViewTransactionHandler {

	private static final String GET_USER_MY_COURSE = "/course/user/favourite/{userId}/entityType/{entityType}/transactionType/{transactionType}";
	
	private static final String GET_USER_VIEW_COURSE = "/transaction/view?entityType={entityType}&entityId={entityId}"
			+ "&transactionType={transactionType}";

	@Autowired
	private RestTemplate restTemplate;

	public List<UserMyCourseDto> getUserMyCourseByEntityIdAndTransactionType(String userId, String entityType, String transactionType)
			throws InvokeException {
		ResponseEntity<UserMyCourseWrapperDto> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("userId", userId);
			params.put("entityType", entityType);
			params.put("transactionType", transactionType);

			StringBuilder path = new StringBuilder();
			path.append(IConstant.VIEW_TRANSACTION_URL).append(GET_USER_MY_COURSE);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, null, UserMyCourseWrapperDto.class, params);
			if (responseEntity.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from View transaction service with error code "
						+ responseEntity.getStatusCode().value());
			}
		} catch (Exception e) {
			if (e instanceof InvokeException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking View transaction service");
			}
		}
		return responseEntity.getBody().getData();
	}
	
	public UserViewCourseDto getUserViewedCourseByEntityIdAndTransactionType(String userId, String entityType, 
				String entityId, String transactionType) throws InvokeException {
		ResponseEntity<UserViewCourseWrapperDto> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("entityType", entityType);
			params.put("transactionType", transactionType);
			params.put("entityId", entityId);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("userId", userId);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			StringBuilder path = new StringBuilder();
			path.append(IConstant.VIEW_TRANSACTION_URL).append(GET_USER_VIEW_COURSE);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, UserViewCourseWrapperDto.class, params);
			if (responseEntity.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from View transaction service with error code "
						+ responseEntity.getStatusCode().value());
			}
		} catch (Exception e) {
			if (e instanceof InvokeException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking View transaction service");
			}
		}
		return responseEntity.getBody().getData();
	}
}
