package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CourseKeyword;
import com.seeka.app.dao.ICourseKeywordDAO;

@Service
@Transactional
public class CourseKeywordService implements ICourseKeywordService {
	
	@Autowired
	ICourseKeywordDAO dao;
	
	@Override
	public void save(CourseKeyword obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CourseKeyword obj) {
		dao.update(obj);
	}
	
	@Override
	public List<CourseKeyword> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<CourseKeyword> searchCourseKeyword(String keyword){
		return dao.searchCourseKeyword(keyword);
	}
}
