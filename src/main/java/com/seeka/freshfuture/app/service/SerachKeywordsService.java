package com.seeka.freshfuture.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.SearchKeywords;
import com.seeka.freshfuture.app.dao.ISearchKeywordsDAO;

@Service
@Transactional
public class SerachKeywordsService implements ISearchKeywordsService {
	
	@Autowired
	ISearchKeywordsDAO dao;
	
	@Override
	public void save(SearchKeywords obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(SearchKeywords obj) {
		dao.update(obj);
	}
	
	@Override
	public List<SearchKeywords> getAll(){
		return dao.getAll();
	}
	
}
