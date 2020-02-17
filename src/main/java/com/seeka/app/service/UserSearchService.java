package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.UserSearchDTO;

public interface UserSearchService {

	UserSearchDTO createUserSearchEntry(UserSearchDTO userSearchDTO);

	void deleteUserSearchEntry(String userId, String entityType);

	List<UserSearchDTO> getUserSearchEntry(String userId, String entityType, Integer startIndex, Integer pageSize);

	int getUserSearchEntryCount(String userId, String entityType);

	List<String> getUserSearchKeyword(Integer startIndex, Integer pageSize, String searchKeyword);

}
