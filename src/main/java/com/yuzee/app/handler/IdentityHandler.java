package com.yuzee.app.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzee.app.dto.NotificationBean;
import com.yuzee.app.dto.PayloadDto;
import com.yuzee.app.dto.UserDeviceInfoDto;
import com.yuzee.app.dto.UserDto;
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
		ResponseEntity<Map> result = restTemplate.postForEntity(IConstant.NOTIFICATION_CONNECTION_URL+ "/push", pushNotification, Map.class);
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
}
