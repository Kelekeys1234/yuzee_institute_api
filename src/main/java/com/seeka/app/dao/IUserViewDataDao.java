package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserViewData;

public interface IUserViewDataDao {

	void createUserViewData(UserViewData userViewData);

	List<Object> getUserViewData(BigInteger userId, String entityType, boolean isUnique);

	int getUserViewDataCountBasedOnUserId(BigInteger userId, BigInteger entityId, String entityType);

	int getUserViewDataCountBasedOnEntityId(BigInteger entityId, String entityType);

}
