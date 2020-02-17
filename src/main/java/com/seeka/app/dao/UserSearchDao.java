package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.UserSearch;

public interface UserSearchDao {

	void createUserSearchEntry(UserSearch userSearch);

	void deleteUserSearchEntry(String userId, String entityType);

	List<UserSearch> getUserSearchEntry(String userId, String entityType, Integer startIndex, Integer pageSize);

	Integer getUserSearchEntryCount(String userId, String entityType);

	List<String> getUserSearchKeyword(Integer startIndex, Integer pageSize, String searchKeyword);

}
