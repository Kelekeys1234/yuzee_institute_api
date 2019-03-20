package com.seeka.app.service;

import java.util.List;

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
	public InstituteServiceDetails get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<InstituteServiceDetails> getAllInstituteByCountry(Integer countryId){
		return dao.getAllInstituteByCountry(countryId);
	}
	
	@Override
	public List<InstituteServiceDetails> getAll(){
		return dao.getAll();
	}

	
}
