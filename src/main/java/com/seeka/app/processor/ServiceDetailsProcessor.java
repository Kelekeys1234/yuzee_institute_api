package com.seeka.app.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.ServiceDetailsDao;

@Service
@Transactional
public class ServiceDetailsProcessor {

	@Autowired
	private ServiceDetailsDao iServiceDetailsDAO;

	public void save(com.seeka.app.bean.Service obj) {
		iServiceDetailsDAO.save(obj);
	}

	public void update(com.seeka.app.bean.Service obj) {
		iServiceDetailsDAO.update(obj);
	}

	public com.seeka.app.bean.Service get(String id) {
		return iServiceDetailsDAO.get(id);
	}

	public List<com.seeka.app.bean.Service> getAllInstituteByCountry(String countryId) {
		return iServiceDetailsDAO.getAllInstituteByCountry(countryId);
	}

	public List<com.seeka.app.bean.Service> getAll() {
		return iServiceDetailsDAO.getAll();
	}

}
