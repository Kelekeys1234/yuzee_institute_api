package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.UserSearchDTO;

public interface UserSearchService {

	UserSearchDTO createUserSearchEntry(UserSearchDTO userSearchDTO);

	void deleteUserSearchEntry(BigInteger userId, String entityType);

	List<UserSearchDTO> getUserSearchEntry(BigInteger userId, String entityType, Integer startIndex, Integer pageSize);

	int getUserSearchEntryCount(BigInteger userId, String entityType);

}
