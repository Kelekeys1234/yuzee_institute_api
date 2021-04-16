package com.yuzee.app.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.GenericResponseDto;
import com.yuzee.app.dto.UserMyCourseDto;
import com.yuzee.app.dto.UserMyCourseWrapperDto;
import com.yuzee.app.dto.UserViewCourseDto;
import com.yuzee.app.dto.UserViewCourseWrapperDto;
import com.yuzee.app.dto.ViewTransactionDto;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.TransactionTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ViewTransactionHandler {

	private static final String GET_USER_MY_COURSE = "/transaction/user/entityType/{entityType}/transactionType/{transactionType}";
	private static final String GET_USER_VIEW_COURSE = "/transaction/user/view?entityType={entityType}&entityId={entityId}"
			+ "&transactionType={transactionType}";
	private static final String GET_USERS_MARK_COURSE_FAVORITE = "/transaction/entityType/{entityType}/entityId/{entityId}/transactionType/{transactionType}";
	private static final String ENTITY_TYPE = "entityType";
	private static final String TRANSACTION_TYPE = "transactionType";
	private static final String ENTTIY_ID = "entityId";
	private static final String USER_ID = "userId";
	
	@Autowired
	private RestTemplate restTemplate;

	public List<ViewTransactionDto> getAllUsersWhoMarkCourseFavorite(String userId, String courseId) throws InvokeException {
		ResponseEntity<GenericResponseDto<List<ViewTransactionDto>>> responseEntity = null;
		Map<String, String> params = new HashMap<>();
		try {
			params.put(ENTITY_TYPE, EntityTypeEnum.COURSE.name());
			params.put(TRANSACTION_TYPE, TransactionTypeEnum.FAVORITE.name());
			params.put(ENTTIY_ID,courseId);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set(USER_ID, userId);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			StringBuilder path = new StringBuilder();
			path.append(IConstant.VIEW_TRANSACTION_URL).append(GET_USERS_MARK_COURSE_FAVORITE);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, new ParameterizedTypeReference<GenericResponseDto<List<ViewTransactionDto>>>() {}, params);
			if (responseEntity.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from View transaction service with error code "
						+ responseEntity.getStatusCode().value());
			}
		} catch (Exception e) {
			log.info("Error invoking View transaction service",e);
			throw new InvokeException("Error invoking View transaction service");
		}	
		return responseEntity.getBody().getData();
	}
	
	public List<UserMyCourseDto> getUserMyCourseByEntityIdAndTransactionType(String userId, String entityType, String transactionType)
			throws InvokeException {
		ResponseEntity<UserMyCourseWrapperDto> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put(ENTITY_TYPE, entityType);
			params.put(TRANSACTION_TYPE, transactionType);

			HttpHeaders headers = new HttpHeaders();
			headers.set(USER_ID, userId);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			StringBuilder path = new StringBuilder();
			path.append(IConstant.VIEW_TRANSACTION_URL).append(GET_USER_MY_COURSE);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, UserMyCourseWrapperDto.class, params);
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
			params.put(ENTITY_TYPE, entityType);
			params.put(TRANSACTION_TYPE, transactionType);
			params.put(ENTTIY_ID, entityId);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set(USER_ID, userId);
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
