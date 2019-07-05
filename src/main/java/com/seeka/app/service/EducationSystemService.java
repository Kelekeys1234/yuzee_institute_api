package com.seeka.app.service;import java.math.BigInteger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.EducationSystem;
import com.seeka.app.dao.IEducationSystemDAO;

@Service
@Transactional
public class EducationSystemService implements IEducationSystemService {

	@Autowired
	private IEducationSystemDAO dao;

	@Override
	public void save(EducationSystem hobbiesObj) {
		dao.save(hobbiesObj);
	}
	
	@Override
	public void update(EducationSystem hobbiesObj) {
		dao.update(hobbiesObj);
	}
	
	@Override
	public List<EducationSystem> getAll() {
		return dao.getAll();
	}
	
	@Override
	public EducationSystem get(BigInteger id) {
		return dao.get(id);
	}
	
	@Override
	public List<EducationSystem> getAllGlobeEducationSystems(){
		return dao.getAllGlobeEducationSystems();
	}
}
