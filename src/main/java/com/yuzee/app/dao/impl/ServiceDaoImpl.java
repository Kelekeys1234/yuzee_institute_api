package com.yuzee.app.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.ServiceRepository;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@Slf4j
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public Service addUpdateService(Service service) throws ValidationException {
		try {
			return serviceRepository.save(service);
		} catch (DataIntegrityViolationException ex) {
			log.error("Service with same name already exists.");
			throw new ValidationException("Service with same name already exists.");
		}
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
	public Service findByNameIgnoreCase(String name) {
		return serviceRepository.findByNameIgnoreCase(name);
	}

}
