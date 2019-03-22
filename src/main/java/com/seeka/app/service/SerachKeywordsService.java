package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.SearchKeywords;
import com.seeka.app.dao.ISearchKeywordsDAO;

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
	
	@Override
	public List<SearchKeywords> searchCourseKeyword(String keyword){
		return dao.searchCourseKeyword(keyword);
	}
}
