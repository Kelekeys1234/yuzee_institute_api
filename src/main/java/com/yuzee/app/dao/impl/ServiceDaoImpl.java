package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.repository.ServiceRepository;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@Slf4j
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public List<Service> addUpdateServices(List<Service> services) throws ValidationException {
		try {
			return serviceRepository.saveAll(services);
		} catch (DataIntegrityViolationException ex) {
			log.error("one or more services contains already existing name");
			throw new ValidationException("one or more services contains already existing name");
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
	public List<Service> getAll() {
		return serviceRepository.findAll();
	}

	@Override
	public List<Service> getAllByIds(List<String> ids) {
		return serviceRepository.findAllById(ids);
	}

	@Override
	public List<Service> findByNameIgnoreCaseIn(List<String> names) {
		return serviceRepository.findByNameIgnoreCaseIn(names);
	}

}
