package com.yuzee.app.processor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;

@org.springframework.stereotype.Service
@Transactional
public class ServiceProcessor {

	@Autowired
	private ServiceDao serviceDao;

	public void save(Service service) {
		serviceDao.addUpdateServiceDetails(service);
	}

	public void update(Service service) {
		serviceDao.addUpdateServiceDetails(service);
	}

	public Optional<Service> getService(String serviceId) {
		return serviceDao.getServiceById(serviceId);
	}

	public Page<Service> getAllServices(final Integer pageNumber, final Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
		return serviceDao.getAllServices(pageable);
	}
}
