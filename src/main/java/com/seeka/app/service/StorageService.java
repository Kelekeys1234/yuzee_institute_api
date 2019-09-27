package com.seeka.app.service;

import java.math.BigInteger;
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
	public List<StorageDto> getStorageInformation(BigInteger entityId, String entityType, String type, String language) throws ValidationException{
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IConstant.STORAGE_CONNECTION_URL);
		
		if(entityId == null || entityId.equals(new BigInteger("0")) ) {
			throw new ValidationException(messageByLocalService.getMessage("not.null", new Object[] {entityId}, language));
		} else if (entityType == null || entityType.isEmpty() ) {
			throw new ValidationException(messageByLocalService.getMessage("not.null", new Object[] {entityType}, language));
		}
		
		builder.queryParam("entityId", entityId);
		builder.queryParam("entityType", entityType);
		if(type!=null) {
			builder.queryParam("type", type);
		}
		
		ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri(), Map.class);
		Map<String, Object> responseMap = result.getBody();
		responseMap.get("data");
		ObjectMapper mapper = new ObjectMapper();
		List<StorageDto> storageDtoList = (List<StorageDto>)mapper.convertValue(responseMap.get("data"), List.class);
		return storageDtoList;
	}
}
