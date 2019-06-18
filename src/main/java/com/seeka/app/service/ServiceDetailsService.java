package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.IServiceDetailsDAO;

@Service
@Transactional
public class ServiceDetailsService implements IServiceDetailsService {
	
	@Autowired
	IServiceDetailsDAO dao;
	
	@Override
	public void save(com.seeka.app.bean.Service obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(com.seeka.app.bean.Service obj) {
		dao.update(obj);
	}
	
	@Override
	public com.seeka.app.bean.Service get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<com.seeka.app.bean.Service> getAllInstituteByCountry(BigInteger countryId){
		return dao.getAllInstituteByCountry(countryId);
	}
	
	@Override
	public List<com.seeka.app.bean.Service> getAll(){
		return dao.getAll();
	}

	
}
