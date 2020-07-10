package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.dto.GlobalSearchKeyword;

public interface IGlobalSearchKeywordDAO {

	void save(GlobalSearchKeyword globalSearchKeyword);
	
	List<String> getOtherUsersTopSearchedKeywords(String userId);
}
