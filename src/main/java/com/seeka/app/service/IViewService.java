package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.UserViewDataRequestDto;

public interface IViewService {

	void createUserViewData(UserViewDataRequestDto userViewDataRequestDto);

	List<UserViewData> getUserViewData(BigInteger userId, String entityType, boolean isUnique, Integer startIndex, Integer pageSize);

	int getUserViewDataCountBasedOnUserId(BigInteger userId, BigInteger entityId, String entityType);

	int getUserViewDataCountBasedOnEntityId(BigInteger entityId, String entityType);

	List<BigInteger> getUserViewDataBasedOnEntityIdList(BigInteger userId, String entityType, List<BigInteger> entityIds);

	List<CourseResponseDto> getUserViewDataCourse(BigInteger userId, boolean isUnique, Integer startIndex, Integer pageSize);

}
