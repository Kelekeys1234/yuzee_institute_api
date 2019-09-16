package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.IGlobalSearchKeywordDAO;
import com.seeka.app.dto.GlobalSearchKeyword;

@Service
@Transactional(rollbackFor = Throwable.class)
public class GlobalSearchKeywordService implements IGlobalSearchKeywordService {

	@Autowired
	private IGlobalSearchKeywordDAO iGlobalSearchKeyWordDao;
	
	@Override
	public void addGlobalSearhcKeyForUser(String searchKeyword, BigInteger userId) {
		
		GlobalSearchKeyword globalSearchKeyword = new GlobalSearchKeyword();
		globalSearchKeyword.setUserId(userId);
		globalSearchKeyword.setUpdatedBy(userId);
		globalSearchKeyword.setCreatedBy(userId);
		globalSearchKeyword.setSearchKeyword(searchKeyword);
		globalSearchKeyword.setCreatedOn(new java.util.Date(System.currentTimeMillis()));
		globalSearchKeyword.setUpdatedOn(new java.util.Date(System.currentTimeMillis()));
		iGlobalSearchKeyWordDao.save(globalSearchKeyword);
	}

	@Override
	public List<String> getOtherUsersTopSearchedKeywords(BigInteger userId) {
		// TODO Auto-generated method stub
		List<String> topKeyWordList = iGlobalSearchKeyWordDao.getOtherUsersTopSearchedKeywords(userId);
		return topKeyWordList;
	}

}
