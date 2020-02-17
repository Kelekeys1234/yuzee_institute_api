package com.seeka.app.service;

import java.util.List;

public interface IGlobalSearchKeywordService {

	void addGlobalSearhcKeyForUser(String searchKeyword, String userId);
	
	List<String> getOtherUsersTopSearchedKeywords(String userId);
}
