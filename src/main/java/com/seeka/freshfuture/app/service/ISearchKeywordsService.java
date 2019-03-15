package com.seeka.freshfuture.app.service;

import java.util.List;

import com.seeka.freshfuture.app.bean.SearchKeywords;

public interface ISearchKeywordsService {
	
	public void save(SearchKeywords obj);
	public void update(SearchKeywords obj);
	public List<SearchKeywords> getAll();
}
