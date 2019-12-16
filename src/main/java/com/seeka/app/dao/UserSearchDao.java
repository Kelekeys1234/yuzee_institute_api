package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.UserSearch;

public interface UserSearchDao {

	void createUserSearchEntry(UserSearch userSearch);

	void deleteUserSearchEntry(BigInteger userId, String entityType);

	List<UserSearch> getUserSearchEntry(BigInteger userId, String entityType, Integer startIndex, Integer pageSize);

	Integer getUserSearchEntryCount(BigInteger userId, String entityType);

}
