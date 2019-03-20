package com.seeka.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.dao.IInstituteTypeDAO;

@Service
@Transactional
public class InstituteTypeService implements IInstituteTypeService {
	
	@Autowired
	IInstituteTypeDAO dao;
	
	@Override
	public void save(InstituteType obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteType obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteType get(Integer id) {
		return dao.get(id);
	}
	
	
}
