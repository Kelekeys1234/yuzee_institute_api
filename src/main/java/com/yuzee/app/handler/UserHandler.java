package com.yuzee.app.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.app.dto.GenericWrapperDto;
import com.yuzee.app.dto.UserInitialInfoDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserHandler {

	@Autowired
	private RestTemplate restTemplate;

	public static final String USER_INITITAL_INFO_URL = IConstant.USER_CONNECTION_URL + "/user/initital/info";

	public List<UserInitialInfoDto> getUserByIds(final List<String> userIds) throws InvokeException {
		ResponseEntity<GenericWrapperDto<List<UserInitialInfoDto>>> response = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(USER_INITITAL_INFO_URL);
			userIds.stream().forEach(e -> builder.queryParam("userIds", e));
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
					new ParameterizedTypeReference<GenericWrapperDto<List<UserInitialInfoDto>>>() {
					});
			if (response.getStatusCode().value() != 200) {
				log.error("Error response recieved from User service with error code {}",
						response.getStatusCode().value());
				throw new InvokeException("Error response recieved from User service with error code "
						+ response.getStatusCode().value());
			}
		} catch (Exception e) {
			log.error("Error invoking User service {}", e);
			if (e instanceof InvokeException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking User service");
			}
		}
		return response.getBody().getData();
	}
}
