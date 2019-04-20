package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteServiceDetails;
import com.seeka.app.dao.IInstituteServiceDetailsDAO;

@Service
@Transactional
public class InstituteServiceDetailsService implements IInstituteServiceDetailsService {
	
	@Autowired
	IInstituteServiceDetailsDAO dao;
	
	@Override
	public void save(InstituteServiceDetails obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteServiceDetails obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteServiceDetails get(UUID id) {
		return dao.get(id);
	}
	
	@Override
	public List<InstituteServiceDetails> getAll(){
		return dao.getAll();
	}
	
	@Override
	public List<String> getAllServices(UUID instituteId){
		return dao.getAllServices(instituteId);
	}

	
}
