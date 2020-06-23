package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteKeywords;
import com.seeka.app.dao.InstituteKeywordDAO;

@Service
@Transactional
public class InstituteKeywordService implements IInstituteKeywordService {
	
	@Autowired
	InstituteKeywordDAO dao;
	
	@Override
	public void save(InstituteKeywords obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteKeywords obj) {
		dao.update(obj);
	}
	
	@Override
	public List<InstituteKeywords> getAll(){
		return dao.getAll();
	}
	
	/*@Override
	public List<InstituteKeywords> searchCourseKeyword(String keyword){
		return dao.searchCourseKeyword(keyword);
	}*/
}
