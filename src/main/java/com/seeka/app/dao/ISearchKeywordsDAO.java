package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.SearchKeywords;

public interface ISearchKeywordsDAO {
	
	public void save(SearchKeywords obj);
	public void update(SearchKeywords obj);
	public List<SearchKeywords> getAll();	
}
