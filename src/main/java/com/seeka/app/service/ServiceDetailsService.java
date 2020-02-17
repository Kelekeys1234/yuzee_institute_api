package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.IServiceDetailsDAO;

@Service
@Transactional
public class ServiceDetailsService implements IServiceDetailsService {

	@Autowired
	private IServiceDetailsDAO iServiceDetailsDAO;

	@Override
	public void save(com.seeka.app.bean.Service obj) {
		iServiceDetailsDAO.save(obj);
	}

	@Override
	public void update(com.seeka.app.bean.Service obj) {
		iServiceDetailsDAO.update(obj);
	}

	@Override
	public com.seeka.app.bean.Service get(String id) {
		return iServiceDetailsDAO.get(id);
	}

	@Override
	public List<com.seeka.app.bean.Service> getAllInstituteByCountry(String countryId) {
		return iServiceDetailsDAO.getAllInstituteByCountry(countryId);
	}

	@Override
	public List<com.seeka.app.bean.Service> getAll() {
		return iServiceDetailsDAO.getAll();
	}

}
