package com.yuzee.app.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.app.constant.Constant;
import com.yuzee.app.dto.FollowerCountDto;
import com.yuzee.app.dto.FollowerCountResponseDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConnectionHandler {

	@Autowired
	private RestTemplate restTemplate;

	private static final String FOLLOW = "/api/v1/follow";

	private static final String FOLLOWERS_COUNT = "/count";

	public FollowerCountDto getFollowersCount(String entityId) throws InvokeException, NotFoundException {
		ResponseEntity<FollowerCountResponseDto> getFollowersCountResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.CONNECTION_BASE_PATH).append(FOLLOW).append(FOLLOWERS_COUNT);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			uriBuilder.pathSegment(entityId);

			getFollowersCountResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
					FollowerCountResponseDto.class);
			if (getFollowersCountResponse.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from storage service with error code "
						+ getFollowersCountResponse.getStatusCode().value());
			}

		} catch (Exception e) {
			log.error("Error invoking connection service {}", e.getMessage());
			if (e instanceof InvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking connection service");
			}
		}
		return getFollowersCountResponse.getBody().getData();
	}
}