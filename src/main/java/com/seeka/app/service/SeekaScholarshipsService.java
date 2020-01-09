package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.SeekaScholarships;
import com.seeka.app.dao.ISeekaScholarshipsDAO;
 
@Service
@Transactional
public class SeekaScholarshipsService implements ISeekaScholarshipsService{
	
	@Autowired
	private ISeekaScholarshipsDAO dao;
    
	@Override
	public void save(SeekaScholarships obj) {
		dao.save(obj);
		
	}
	@Override
	public List<SeekaScholarships> getAll() {		
		return dao.getAll();
	}
	
	@Override
	public SeekaScholarships get(BigInteger id) {
		return dao.get(id);
	}
	

}
