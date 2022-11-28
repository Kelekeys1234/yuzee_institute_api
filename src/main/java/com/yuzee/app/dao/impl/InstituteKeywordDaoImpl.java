package com.yuzee.app.dao.impl;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteKeywords;
import com.yuzee.app.dao.InstituteKeywordDao;
import com.yuzee.app.repository.InstituteKeywordRepository;

@Repository
public class InstituteKeywordDaoImpl implements InstituteKeywordDao {
	
	@Autowired
	private InstituteKeywordRepository instituteKeywordRepository;
	
	
	@Override
	public void save(InstituteKeywords obj) {
		instituteKeywordRepository.save(obj);					
	}
	
	@Override
	public void update(InstituteKeywords obj) {	
		instituteKeywordRepository.save(obj);
	}
	
	@Override
	public List<InstituteKeywords> getAll() {
		return instituteKeywordRepository.findAll();
	}
}
