package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.SeekaPopup;
import com.seeka.app.dao.ISeekaPopupDAO;
 
@Service
@Transactional
public class SeekaPopupService implements ISeekaPopupService{
	
	@Autowired
	ISeekaPopupDAO dao;
    
	@Override
	public void save(SeekaPopup obj) {
		dao.save(obj);
		
	}
	@Override
	public List<SeekaPopup> getAll() {		
		return dao.getAll();
	}
	
	@Override
	public SeekaPopup get(BigInteger id) {
		return dao.get(id);
	}
		

}
