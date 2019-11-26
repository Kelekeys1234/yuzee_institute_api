package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserViewData;

public interface IViewDao {

	void createUserViewData(UserViewData userViewData);

	List<Object> getUserViewData(BigInteger userId, String entityType, boolean isUnique, Integer startIndex, Integer pageSize);

	int getUserViewDataCountBasedOnUserId(BigInteger userId, BigInteger entityId, String entityType);

	int getUserViewDataCountBasedOnEntityId(BigInteger entityId, String entityType);

	List<Object> getUserViewDataBasedOnEntityIdList(BigInteger userId, String entityType, boolean isUnique, List<BigInteger> entityIds);

	List<BigInteger> getUserWatchCourseIds(final BigInteger userId, final String entityType);

	List<BigInteger> getOtherUserWatchCourse(BigInteger userId, String entityType);
}
