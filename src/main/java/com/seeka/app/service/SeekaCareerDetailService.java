package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.SeekaCareerDetail;
import com.seeka.app.dao.ISeekaCareerDetailDAO;
 
@Service
@Transactional
public class SeekaCareerDetailService implements ISeekaCareerDetailService{
	
	@Autowired
	private ISeekaCareerDetailDAO dao;
    
	@Override
	public void save(SeekaCareerDetail obj) {
		dao.save(obj);
		
	}
	@Override
	public List<SeekaCareerDetail> getAll() {		
		return dao.getAll();
	}
	
	@Override
	public SeekaCareerDetail get(String id) {
		return dao.get(id);
	}
	

	

}
