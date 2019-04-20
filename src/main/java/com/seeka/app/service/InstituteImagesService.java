package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteImages;
import com.seeka.app.dao.IInstituteImagesDAO;
 

@Service
@Transactional
public class InstituteImagesService implements IInstituteImagesService{
	
	@Autowired
	IInstituteImagesDAO dao;
	
	@Override
	public void save(InstituteImages obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteImages obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteImages get(UUID id) {
		return dao.get(id);
	}
	
	@Override
	public List<InstituteImages> getAll(){
		return dao.getAll();
	} 

}
