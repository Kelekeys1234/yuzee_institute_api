package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteKeyword;
import com.seeka.app.dao.IInstituteKeywordDAO;

@Service
@Transactional
public class InstituteKeywordService implements IInstituteKeywordService {
	
	@Autowired
	IInstituteKeywordDAO dao;
	
	@Override
	public void save(InstituteKeyword obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteKeyword obj) {
		dao.update(obj);
	}
	
	@Override
	public List<InstituteKeyword> getAll(){
		return dao.getAll();
	}
	
	/*@Override
	public List<InstituteKeyword> searchCourseKeyword(String keyword){
		return dao.searchCourseKeyword(keyword);
	}*/
}
