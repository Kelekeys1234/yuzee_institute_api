package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.UserCourseView;
import com.seeka.app.dto.UserViewDataRequestDto;
import com.seeka.app.exception.ValidationException;

public interface IViewService {

	void createUserViewData(UserViewDataRequestDto userViewDataRequestDto);

	List<UserViewData> getUserViewData(BigInteger userId, String entityType, boolean isUnique, Integer startIndex, Integer pageSize);

	int getUserViewDataCountBasedOnUserId(String userId, String entityId, String entityType);

	int getUserViewDataCountBasedOnEntityId(BigInteger entityId, String entityType);

	List<BigInteger> getUserViewDataBasedOnEntityIdList(String userId, String entityType, List<String> entityIds);

	List<CourseResponseDto> getUserViewDataCourse(BigInteger userId, boolean isUnique, Integer startIndex, Integer pageSize);

	List<UserCourseView> userVisistedCourseBasedOncity(String cityName, Date fromDate, Date toDate) throws ValidationException;

	List<UserCourseView> userVisistedCourseBasedOnCountry(String countryName, Date fromDate, Date toDate) throws ValidationException;

}
