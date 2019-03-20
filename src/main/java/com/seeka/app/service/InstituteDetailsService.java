package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.dao.IInstituteDetailsDAO;

@Service
@Transactional
public class InstituteDetailsService implements IInstituteDetailsService {
	
	@Autowired
	IInstituteDetailsDAO dao;
	
	@Override
	public void save(InstituteDetails obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteDetails obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteDetails get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<InstituteDetails> getAllInstituteByCountry(Integer countryId){
		return dao.getAllInstituteByCountry(countryId);
	}
	
	@Override
	public List<InstituteDetails> getAll(){
		return dao.getAll();
	}

	
}
