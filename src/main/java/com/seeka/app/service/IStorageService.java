package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.ValidationException;

public interface IStorageService {

	List<StorageDto> getStorageInformation(BigInteger entityId, String entityType, String type, String language) throws ValidationException;

	List<StorageDto> getStorageInformationBasedOnEntityIdList(List<BigInteger> entityIds, String entityType, String type, String language)
			throws ValidationException;
}
