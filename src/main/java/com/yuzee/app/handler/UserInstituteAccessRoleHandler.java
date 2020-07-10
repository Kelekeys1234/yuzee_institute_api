package com.yuzee.app.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.constant.Constant;
import com.yuzee.app.dto.UserInstituteAccessInternalResponseDto;
import com.yuzee.app.dto.UserInstituteAccessRoleInternalResponseDtoWrapper;
import com.yuzee.app.exception.InvokeException;


@Component
public class UserInstituteAccessRoleHandler {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UserInstituteAccessRoleHandler.class);

	@Autowired
	private RestTemplate restTemplate;

	private static final String GET_USER_INSTITUTE_ACCESS_BY_INSTITUTE_ID = "/api/v1/institute/internal/user/access/{instituteId}?status={status}";

	public List<UserInstituteAccessInternalResponseDto> getUserInstituteAccessInternal (String instituteId, String status) throws Exception {
		ResponseEntity<UserInstituteAccessRoleInternalResponseDtoWrapper> listOfUserInsituteAccessInternalResponseDto = null;
		Map<String, String> params = new HashMap<String, String>();
	    params.put("instituteId", instituteId);
	    params.put("status", status);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.IDENTITY_BASE_PATH).append(GET_USER_INSTITUTE_ACCESS_BY_INSTITUTE_ID);
			LOGGER.info("Calling identity service to fetch user access for institute Id {}", instituteId);
			listOfUserInsituteAccessInternalResponseDto = restTemplate.exchange(path.toString(), HttpMethod.GET, entity,
					UserInstituteAccessRoleInternalResponseDtoWrapper.class,params);
			if (listOfUserInsituteAccessInternalResponseDto.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from identity service with error code "
						+ listOfUserInsituteAccessInternalResponseDto.getStatusCode().value());
			}

		} catch (Exception e) {
			LOGGER.error("Error invoking identity service {}", e);
			if (e instanceof InvokeException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking identity service");
			}
		}
		return listOfUserInsituteAccessInternalResponseDto.getBody().getData();
	}


}
