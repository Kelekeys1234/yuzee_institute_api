package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.GlobalSearchKeyword;

public interface IGlobalSearchKeywordDAO {

	void save(GlobalSearchKeyword globalSearchKeyword);
	
	List<String> getOtherUsersTopSearchedKeywords(BigInteger userId);
}
