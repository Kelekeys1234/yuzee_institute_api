package com.yuzee.app.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.app.constant.Constant;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.dto.StorageDtoWrapper;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageHandler {
	@Autowired
	private RestTemplate restTemplate;

	private static final String GET_STORAGE = "/api/v1/storage";
	private static final String DELETE_BY_ID = "/api/v1/storage/entityId/";

	public List<StorageDto> getStorages(String entityId, EntityTypeEnum entityType, EntitySubTypeEnum entitySubType)
			throws InvokeException, NotFoundException {
		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entityId);
		return getStorages(entityIds, entityType, entitySubType);
	}

	public List<StorageDto> getStorages(List<String> entityIds, EntityTypeEnum entityType,
			EntitySubTypeEnum entitySubType) throws InvokeException, NotFoundException {
		ResponseEntity<StorageDtoWrapper> getStoragesResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.STORAGE_BASE_PATH).append(GET_STORAGE);
			// adding the query params to the URL
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			entityIds.stream().forEach(e -> uriBuilder.queryParam("entity_id", e));
			uriBuilder.queryParam("entity_type", entityType.name());
			uriBuilder.queryParam("entity_sub_type", entitySubType.name());
			log.info("Calling storage service to fetch certificates for entity Id {}",
					entityIds.stream().map(Object::toString).collect(Collectors.joining(",")).toString());
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

	public void deleteStorageBasedOnEntityId(String entityId) throws InvokeException, NotFoundException {
		ResponseEntity<Void> response;
		StringBuilder path = new StringBuilder();
		path.append(Constant.STORAGE_BASE_PATH).append(DELETE_BY_ID).append(entityId);
		// adding the query params to the URL
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
		log.info("Calling storage service to delete certificates for asset Id {}", entityId);
		response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.DELETE, null, Void.class);
		if (response.getStatusCode().value() != 200) {
			throw new InvokeException(
					"Error response recieved from storage service with error code " + response.getStatusCode().value());
		}
	}

	public String uploadFileInStorage(final MultipartFile file, final String entityId, EntityTypeEnum entityType,
			EntitySubTypeEnum entitySubType) throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			MultiValueMap<String, Object> formDate = new LinkedMultiValueMap<>();
			File newFile = convert(file);
			formDate.add("file", new FileSystemResource(newFile));

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL);
			builder.queryParam("entity_id", entityId);
			builder.queryParam("entity_type", entityType.name());
			builder.queryParam("entity_sub_type", entitySubType.name());
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formDate, headers);
			response = restTemplate.postForEntity(builder.toUriString(), request, Map.class);
			newFile.delete();
		} catch (Exception e) {
			log.error("Error invoking storage service {}", e);
			if (e instanceof NotFoundException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking storage service");
			}
		}
		Object data = response.getBody().get("data");
		return String.valueOf((String) data);
	}

	private File convert(final MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
