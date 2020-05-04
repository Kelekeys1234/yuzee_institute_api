package com.seeka.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.StorageRequestDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.message.MessageByLocaleService;
import com.seeka.app.util.IConstant;

@Service
@Transactional(rollbackFor = Throwable.class)
public class StorageService implements IStorageService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MessageByLocaleService messageByLocalService;

	@Override
	public List<StorageDto> getStorageInformation(final String entityId, String entityType, final String type, final String language)
			throws ValidationException {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL);

		if (entityId == null || entityId.isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("not.null", new Object[] { entityId }, language));
        } else if (entityType == null || entityType.isEmpty()) {
            throw new ValidationException(messageByLocalService.getMessage("not.null", new Object[] { entityType }, language));
        }

		builder.queryParam("entityId", entityId);
		builder.queryParam("entityType", entityType);
		if (type != null) {
			builder.queryParam("type", type);
		}

		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		List<StorageDto> storageDtoList = mapper.convertValue(responseMap.get("data"), List.class);
		List<StorageDto> resultList = new ArrayList<>();
		for (Object obj : storageDtoList) {
			StorageDto storageDto1 = mapper.convertValue(obj, StorageDto.class);
			resultList.add(storageDto1);
		}

		return resultList;
	}

	@Override
	public List<StorageDto> getStorageInformationBasedOnEntityIdList(final List<String> entityIds, String entityType, final String type,
			final String language) throws ValidationException {
		String url = IConstant.STORAGE_CONNECTION_URL + "/get";

		if (entityIds == null || entityIds.isEmpty()) {
			throw new ValidationException(messageByLocalService.getMessage("not.null", new Object[] { entityIds }, language));
        } else if (entityType == null || entityType.isEmpty()) {
            throw new ValidationException(messageByLocalService.getMessage("not.null", new Object[] { entityType }, language));
        }

		StorageRequestDto storageRequestDto = new StorageRequestDto();
		storageRequestDto.setEntityIds(entityIds);
		storageRequestDto.setEntityType(entityType);
		if (type != null) {
			storageRequestDto.setType(type);
		}
		ResponseEntity<Map> result = restTemplate.postForEntity(url, storageRequestDto, Map.class);
		Map<String, Object> responseMap = result.getBody();
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		List<StorageDto> storageDtoList = mapper.convertValue(responseMap.get("data"), List.class);
		List<StorageDto> resultList = new ArrayList<>();
		for (Object obj : storageDtoList) {
			StorageDto storageDto1 = mapper.convertValue(obj, StorageDto.class);
			resultList.add(storageDto1);
		}
		return resultList;
	}
	
	@Override
	public void deleteStorageBasedOnEntityId(final String entityId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL + "/deleteStorage");
		builder.queryParam("entityId", entityId);
		restTemplate.delete(builder.build().toUri());
	}
}
