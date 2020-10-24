package com.yuzee.app.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzee.app.constant.Constant;
import com.yuzee.app.dto.InstituteProfilePermissionDto;
import com.yuzee.app.dto.InstituteProfilePermissionWrapperDto;
import com.yuzee.app.dto.NotificationBean;
import com.yuzee.app.dto.PayloadDto;
import com.yuzee.app.dto.UserDeviceInfoDto;
import com.yuzee.app.dto.UserDto;
import com.yuzee.app.dto.VoidDataResponseDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class IdentityHandler {

	@Autowired
	private RestTemplate restTemplate;

	private static final String USER_PROFILE_DATA_PERMISSION = "/api/v1/user/profile/data/permission";
	
	private static final String GET_USER_PROFILE_DATA_PERMISSION_MAP = "/map";
	
	public UserDto getUserById(final String userId) throws ValidationException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.USER_DETAIL_CONNECTION_URL).pathSegment(String.valueOf(userId));
		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		Integer status = (Integer) responseMap.get("status");
		System.out.println(status);
		if (status != 200) {
			throw new ValidationException((String) responseMap.get("message"));
		}
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		UserDto userDto = mapper.convertValue(responseMap.get("data"), UserDto.class);

		return userDto;
	}

	private List<UserDeviceInfoDto> getUserDeviceById(final String userId) throws ValidationException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.USER_DEVICE_CONNECTION_URL).pathSegment(String.valueOf(userId));
		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		Integer status = (Integer) responseMap.get("status");
		System.out.println(status);
		if (status != 200) {
			System.out.println((String) responseMap.get("message"));
			return null;
		}
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		List<UserDeviceInfoDto> userDeviceInfoDto = mapper.convertValue(responseMap.get("data"), List.class);
		return userDeviceInfoDto;
	}

	private void sendPushNotification(final UserDeviceInfoDto userDeviceInfoDto, final String message, final String notificationType) {
		NotificationBean pushNotification = new NotificationBean();
		PayloadDto payloadDto = new PayloadDto();

		payloadDto.setPlatform(userDeviceInfoDto.getPlatform());
		payloadDto.setDeviceId(userDeviceInfoDto.getDeviceId());
		payloadDto.setContent(message);
		payloadDto.setTitle("Application Update");

		pushNotification.setChannel("PUSH");
		pushNotification.setTenantCode("Yuzee");
		pushNotification.setUser(userDeviceInfoDto.getFirstName() + " " + userDeviceInfoDto.getLastName());
		pushNotification.setPayload(payloadDto);
		pushNotification.setUserId(userDeviceInfoDto.getUserId());
		pushNotification.setNotificationType(notificationType);
		ResponseEntity<Map> result = restTemplate.postForEntity(IConstant.NOTIFICATION_CONNECTION_URL, pushNotification, Map.class);
		System.out.println(result.getStatusCode());
	}

	public void sendPushNotification(final String userId, final String message, final String notificationType) throws ValidationException {
		List<UserDeviceInfoDto> userDeviceInfoDto = getUserDeviceById(userId);
		if (userDeviceInfoDto != null) {
			ObjectMapper objMapper = new ObjectMapper();
			for (Object obj : userDeviceInfoDto) {
				UserDeviceInfoDto userDevInfoDto = objMapper.convertValue(obj, UserDeviceInfoDto.class);
				sendPushNotification(userDevInfoDto, message, notificationType);
			}
		}
	}

	public Map<String, String> getUserProfileDataPermission(List<String> entityIds)
			throws InvokeException, NotFoundException {
		ResponseEntity<InstituteProfilePermissionWrapperDto> getUserProfileDataPermissionResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.USER_BASE_PATH).append(USER_PROFILE_DATA_PERMISSION)
					.append(GET_USER_PROFILE_DATA_PERMISSION_MAP);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			entityIds.stream().forEach(e -> uriBuilder.queryParam("entityIds", e));
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<>(headers);
			getUserProfileDataPermissionResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request,
					InstituteProfilePermissionWrapperDto.class);
			if (getUserProfileDataPermissionResponse.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from user service with error code "
						+ getUserProfileDataPermissionResponse.getStatusCode().value());
			}
		} catch (Exception e) {
			log.error("Error invoking user service {}", e.getMessage());
			if (e instanceof InvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking user service");
			}
		}
		Map<String, String> map = new HashMap<>(); 
		
		List<InstituteProfilePermissionDto> dtos = getUserProfileDataPermissionResponse.getBody().getData();
		if (!CollectionUtils.isEmpty(dtos)) {
			map = dtos.stream().collect(Collectors.toMap(InstituteProfilePermissionDto::getEntityId,
					InstituteProfilePermissionDto::getEntityPermission));
		}
		return map;
	}
	
	public void saveOrUpdateUserProfileDataPermission(String userId,String entityType, String entityId, String permission)
			throws InvokeException, NotFoundException {
		ResponseEntity<VoidDataResponseDto> response = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.USER_BASE_PATH).append(USER_PROFILE_DATA_PERMISSION);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			uriBuilder.queryParam("entity_id", entityId);
			uriBuilder.queryParam("entity_type", entityType);
			uriBuilder.queryParam("permission", permission);

			HttpHeaders headers = new HttpHeaders();
			headers.add("userId", userId);
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<>(headers);
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,
					request, VoidDataResponseDto.class);
			if (response.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from user service with error code "
						+ response.getStatusCode().value());
			}

		} catch (Exception e) {
			log.error("Error invoking user service {}", e.getMessage());
			if (e instanceof InvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking user service");
			}
		}
	}
}
