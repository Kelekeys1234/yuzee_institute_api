package com.yuzee.app.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.GlobalSearchKeyword;
import com.yuzee.app.repository.GlobalSearchKeywordRepository;

@Repository
public class GlobalSearchKeywordDAO implements IGlobalSearchKeywordDAO {

	 @Autowired
	 private GlobalSearchKeywordRepository globalSearchKeywordRepository;
	 
	 private MongoTemplate mongoTemplate;

	@Override
	public void save(final GlobalSearchKeyword globalSearchKeyword) {
		globalSearchKeywordRepository.save(globalSearchKeyword);
	}

	@Override
	public List<String> getOtherUsersTopSearchedKeywords(String userId) {
		 Query query = new Query();
	     query.addCriteria(Criteria.where("userId").is(userId));
	     List<GlobalSearchKeyword> keyWord=mongoTemplate.find(query, GlobalSearchKeyword.class , "global_search_keyword");
	     String keyWordLists = keyWord.stream().map(e->e.getSearchKeyword()).toString();
	     List<String> keyWordList = Arrays.asList(keyWordLists);
		return keyWordList;
	}

}
