package com.yuzee.app.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.app.constant.Constant;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.dto.StorageDtoWrapper;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageHandler {
	@Autowired
	private RestTemplate restTemplate;

	private static final String GET_STORAGE = "/api/v1/storage";

	public List<StorageDto> getStorages(String entityId, EntityTypeEnum entityType,
			EntitySubTypeEnum entitySubType) throws Exception {
		ResponseEntity<StorageDtoWrapper> getStoragesResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.STORAGE_BASE_PATH).append(GET_STORAGE);
			// adding the query params to the URL
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			uriBuilder.queryParam("entity_id", entityId);
			uriBuilder.queryParam("entity_type", entityType.name());
			uriBuilder.queryParam("entity_sub_type", entitySubType.name());
			log.info("Calling storage service to fetch certificates for asset Id {}", entityId);
			getStoragesResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
					StorageDtoWrapper.class);
			if (getStoragesResponse.getStatusCode().value() != 200) {
				throw new InvokeException("Error response recieved from storage service with error code "
						+ getStoragesResponse.getStatusCode().value());
			}

		} catch (Exception e) {
			log.error("Error invoking storage service {}", e);
			if (e instanceof InvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking storage service");
			}
		}
		return getStoragesResponse.getBody().getData();
	}
}
