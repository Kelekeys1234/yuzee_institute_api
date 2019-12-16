package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.UserSearch;
import com.seeka.app.dao.UserSearchDao;
import com.seeka.app.dto.UserSearchDTO;

@Service
@Transactional
public class UserSearchServiceImpl implements UserSearchService {

	@Autowired
	private UserSearchDao iUserSearchDao;

	@Override
	public UserSearchDTO createUserSearchEntry(final UserSearchDTO userSearchDTO) {
		UserSearch userSearch = new UserSearch();
		BeanUtils.copyProperties(userSearchDTO, userSearch);
		iUserSearchDao.createUserSearchEntry(userSearch);
		userSearchDTO.setId(userSearch.getId());
		return userSearchDTO;
	}

	@Override
	public void deleteUserSearchEntry(final BigInteger userId, final String entityType) {
		iUserSearchDao.deleteUserSearchEntry(userId, entityType);
	}

	@Override
	public List<UserSearchDTO> getUserSearchEntry(final BigInteger userId, final String entityType, final Integer startIndex, final Integer pageSize) {
		List<UserSearch> userSearchList = iUserSearchDao.getUserSearchEntry(userId, entityType, startIndex, pageSize);
		List<UserSearchDTO> userSearchDtoList = new ArrayList<>();
		for (UserSearch userSearch : userSearchList) {
			UserSearchDTO userSearchDto = new UserSearchDTO();
			BeanUtils.copyProperties(userSearch, userSearchDto);
			userSearchDtoList.add(userSearchDto);
		}
		return userSearchDtoList;
	}

	@Override
	public int getUserSearchEntryCount(final BigInteger userId, final String entityType) {
		return iUserSearchDao.getUserSearchEntryCount(userId, entityType);
	}
}
