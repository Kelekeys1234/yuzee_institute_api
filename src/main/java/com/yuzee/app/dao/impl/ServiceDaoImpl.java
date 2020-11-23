package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.repository.ServiceRepository;

@Component
@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public void addUpdateServiceDetails(Service service) {
		serviceRepository.save(service);
	}

	@Override
	public Optional<Service> getServiceById(String id) {
		return serviceRepository.findById(id);
	}

	@Override
	public Page<Service> getAllServices(Pageable pageable) {
		return serviceRepository.findAll(pageable);
	}

	@Override
	public List<Service> getAllByIds(List<String> ids) {
		return serviceRepository.findAllById(ids);
	}

}
