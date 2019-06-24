package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.SeekaCoupons;
import com.seeka.app.dao.ISeekaCouponsDAO;
 
@Service
@Transactional
public class SeekaCouponsService implements ISeekaCouponsService{
	
	@Autowired
	ISeekaCouponsDAO dao;
    
	@Override
	public void save(SeekaCoupons obj) {
		dao.save(obj);
		
	}
	@Override
	public List<SeekaCoupons> getAll() {		
		return dao.getAll();
	}
	
	@Override
	public SeekaCoupons get(BigInteger id) {
		return dao.get(id);
	}
	
}
