package com.seeka.freshfuture.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.freshfuture.app.bean.Institute;
import com.seeka.freshfuture.app.dao.IInstituteDAO;

@Service
@Transactional
public class InstituteTypeService implements IInstituteService {
	
	@Autowired
	IInstituteDAO dao;
	
	@Override
	public void save(Institute obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(Institute obj) {
		dao.update(obj);
	}
	
	@Override
	public Institute get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<Institute> getAllInstituteByCountry(Integer countryId){
		return dao.getAllInstituteByCountry(countryId);
	}
	
}
