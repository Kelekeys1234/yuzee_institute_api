package com.yuzee.app.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.dao.ServiceDetailsDao;

@Service
@Transactional
public class ServiceDetailsProcessor {

	@Autowired
	private ServiceDetailsDao serviceDetailsDAO;

	public void save(com.yuzee.app.bean.Service service) {
		serviceDetailsDAO.addUpdateServiceDetails(service);
	}

	public void update(com.yuzee.app.bean.Service service) {
		serviceDetailsDAO.addUpdateServiceDetails(service);
	}

	public com.yuzee.app.bean.Service getService(String serviceId) {
		return serviceDetailsDAO.getService(serviceId);
	}

	public List<com.yuzee.app.bean.Service> getAllInstituteByCountry(String countryId) {
		return serviceDetailsDAO.getAllInstituteByCountry(countryId);
	}

	public List<com.yuzee.app.bean.Service> getAllServices() {
		return serviceDetailsDAO.getAllServices();
	}
}
