package com.seeka.freshfuture.app.dao;

import java.util.List;

import com.seeka.freshfuture.app.bean.SearchKeywords;

public interface ISearchKeywordsDAO {
	
	public void save(SearchKeywords obj);
	public void update(SearchKeywords obj);
	public List<SearchKeywords> getAll();	
}
