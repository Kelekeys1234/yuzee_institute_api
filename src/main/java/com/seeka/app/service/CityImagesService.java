package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.CityImages;
import com.seeka.app.dao.ICityImagesDAO;
 

@Service
@Transactional
public class CityImagesService implements ICityImagesService{
	
	@Autowired
	ICityImagesDAO dao;
	
	@Override
	public void save(CityImages obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(CityImages obj) {
		dao.update(obj);
	}
	
	@Override
	public CityImages get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<CityImages> getAll(){
		return dao.getAll();
	} 

}
