package com.seeka.app.service;import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteType;
import com.seeka.app.dao.IInstituteTypeDAO;

@Service
@Transactional
public class InstituteTypeService implements IInstituteTypeService {
	
	@Autowired
	private IInstituteTypeDAO iInstituteTypeDAO;
	
	@Override
	public void save(InstituteType instituteType) {
		Date today = new Date();
		instituteType.setCreatedOn(today);
		instituteType.setUpdatedOn(today);
		iInstituteTypeDAO.save(instituteType);
	}
	
	@Override
	public void update(InstituteType instituteType) {
		Date today = new Date();
		instituteType.setUpdatedOn(today);
		iInstituteTypeDAO.update(instituteType);
	}
	
	@Override
	public InstituteType get(BigInteger id) {
		return iInstituteTypeDAO.get(id);
	}
	
	
}
