package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.ValidationException;

public interface IStorageService {

	List<StorageDto> getStorageInformation(String entityId, String type, String language) throws ValidationException;

	List<StorageDto> getStorageInformationBasedOnEntityIdList(List<String> entityIds, String type, String language)
			throws ValidationException;
}
