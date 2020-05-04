package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.ValidationException;

public interface IStorageService {

	List<StorageDto> getStorageInformation(String entityId, String entityType, String type, String language) throws ValidationException;

	List<StorageDto> getStorageInformationBasedOnEntityIdList(List<String> entityIds, String entityType, String type, String language)
			throws ValidationException;

	void deleteStorageBasedOnEntityId(String entityId);
}
