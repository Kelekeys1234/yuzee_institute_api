package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.SearchKeywords;

public interface ISearchKeywordsService {
	
	public void save(SearchKeywords obj);
	public void update(SearchKeywords obj);
	public List<SearchKeywords> getAll();
	public List<SearchKeywords> searchCourseKeyword(String keyword);
}
