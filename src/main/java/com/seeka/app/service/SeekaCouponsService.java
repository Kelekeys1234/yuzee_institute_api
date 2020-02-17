package com.seeka.app.service;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.SeekaCoupons;
import com.seeka.app.dao.ISeekaCouponsDAO;
 
@Service
@Transactional
public class SeekaCouponsService implements ISeekaCouponsService{
	
	@Autowired
	private ISeekaCouponsDAO dao;
    
	@Override
	public void save(SeekaCoupons obj) {
		dao.save(obj);
		
	}
	@Override
	public List<SeekaCoupons> getAll() {		
		return dao.getAll();
	}
	
	@Override
	public SeekaCoupons get(String id) {
		return dao.get(id);
	}
	
}
