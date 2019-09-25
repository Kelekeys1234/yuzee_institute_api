package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeka.app.dto.NotificationBean;
import com.seeka.app.dto.PayloadDto;
import com.seeka.app.dto.UserDeviceInfoDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsersService implements IUsersService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserDto getUserById(final BigInteger userId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.USER_DETAIL_CONNECTION_URL).pathSegment(String.valueOf(userId));
		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		UserDto userDto = mapper.convertValue(responseMap.get("data"), UserDto.class);

		return userDto;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserDeviceInfoDto getUserDeviceById(final BigInteger userId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.USER_DEVICE_CONNECTION_URL).pathSegment(String.valueOf(userId));
		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		UserDeviceInfoDto userDeviceInfoDto = mapper.convertValue(responseMap.get("data"), UserDeviceInfoDto.class);

		return userDeviceInfoDto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void sendPushNotification(final UserDeviceInfoDto userDeviceInfoDto, final String message) {
		NotificationBean pushNotification = new NotificationBean();
		PayloadDto payloadDto = new PayloadDto();

		payloadDto.setPlatform(userDeviceInfoDto.getPlatform());
		payloadDto.setDeviceId(userDeviceInfoDto.getDeviceId());
		payloadDto.setContent(message);
		payloadDto.setTitle("SEEKA");

		pushNotification.setChannel("PUSH");
		pushNotification.setTenantCode("SEEKA-DEGREE");
		pushNotification.setUser(userDeviceInfoDto.getFirstName() + " " + userDeviceInfoDto.getLastName());
		pushNotification.setPayload(payloadDto);

		ResponseEntity<Map> result = restTemplate.postForEntity(IConstant.NOTIFICATION_CONNECTION_URL, pushNotification, Map.class);
		System.out.println(result.getStatusCode());
	}

	@Override
	public void sendPushNotification(final BigInteger userId, final String message) {
		UserDeviceInfoDto userDeviceInfoDto = getUserDeviceById(userId);
		sendPushNotification(userDeviceInfoDto, message);
	}

}
