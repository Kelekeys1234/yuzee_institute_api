package com.seeka.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.ServiceDetails;
import com.seeka.app.dao.IServiceDetailsDAO;

@Service
@Transactional
public class ServiceDetailsService implements IServiceDetailsService {
	
	@Autowired
	IServiceDetailsDAO dao;
	
	@Override
	public void save(ServiceDetails obj) {
		dao.save(obj);
	}
	
	@Override
	public void update(ServiceDetails obj) {
		dao.update(obj);
	}
	
	@Override
	public ServiceDetails get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public List<ServiceDetails> getAllInstituteByCountry(Integer countryId){
		return dao.getAllInstituteByCountry(countryId);
	}
	
	@Override
	public List<ServiceDetails> getAll(){
		return dao.getAll();
	}

	
}
