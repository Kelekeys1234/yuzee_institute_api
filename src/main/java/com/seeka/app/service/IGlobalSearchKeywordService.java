package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

public interface IGlobalSearchKeywordService {

	void addGlobalSearhcKeyForUser(String searchKeyword, BigInteger userId);
	
	List<String> getOtherUsersTopSearchedKeywords(BigInteger userId);
}
