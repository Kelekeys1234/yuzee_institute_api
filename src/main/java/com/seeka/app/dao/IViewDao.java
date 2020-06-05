package com.seeka.app.dao;

import java.util.Date;
import java.util.List;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.dto.UserCourseView;

public interface IViewDao {

	void createUserViewData(UserViewData userViewData);

	List<Object> getUserViewData(String userId, String entityType, boolean isUnique, Integer startIndex, Integer pageSize);

	int getUserViewDataCountBasedOnUserId(String userId, String entityId, String entityType);

	int getUserViewDataCountBasedOnEntityId(String entityId, String entityType);

	List<Object> getUserViewDataBasedOnEntityIdList(String userId, String entityType, boolean isUnique, List<String> entityIds);

	List<String> getUserWatchCourseIds(final String userId, final String entityType);

	List<String> getOtherUserWatchCourse(String userId, String entityType);

	List<UserCourseView> userVisistedCourseBasedOncity(String cityId, Date fromDate, Date toDate);

	List<UserCourseView> userVisistedCourseBasedOnCountry(String countryId, Date fromDate, Date toDate);
	
	public List<String> getRandomUserWatchCourseIds(String userId, String entityType, Integer startIndex, Integer pageSize);
}
