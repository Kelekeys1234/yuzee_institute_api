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
import com.seeka.app.dto.UserDto;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsersService implements IUsersService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@SuppressWarnings("unchecked")
	public UserDto getUserById(final BigInteger userId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.USER_DETAIL_CONNECTION_URL).pathSegment(String.valueOf(userId));
		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		UserDto userDto = mapper.convertValue(responseMap.get("data"), UserDto.class);

		return userDto;
	}

}
