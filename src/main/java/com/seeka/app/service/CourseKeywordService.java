package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseKeywords;
import com.seeka.app.dao.ICourseKeywordDAO;

@Service
@Transactional
public class CourseKeywordService implements ICourseKeywordService {
	
	@Autowired
	ICourseKeywordDAO dao;
	
	@Override
	public void save(CourseKeywords obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CourseKeywords obj) {
		dao.update(obj);
	}
	
	@Override
	public List<CourseKeywords> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<CourseKeywords> searchCourseKeyword(String keyword){
		return dao.searchCourseKeyword(keyword);
	}
}
