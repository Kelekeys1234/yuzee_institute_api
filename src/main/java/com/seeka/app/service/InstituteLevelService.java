package com.seeka.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.dao.IInstituteLevelDAO;

@Service
@Transactional
public class InstituteLevelService implements IInstituteLevelService {
	
	@Autowired
	IInstituteLevelDAO dao;
	
	@Override
	public void save(InstituteLevel obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(InstituteLevel obj) {
		dao.update(obj);
	}
	
	@Override
	public InstituteLevel get(UUID id) {
		return dao.get(id);
	}
	
	@Override
	public List<InstituteLevel> getAllLevelByInstituteId(UUID instituteId){
		return dao.getAllLevelByInstituteId(instituteId);
	}
	
	
}
