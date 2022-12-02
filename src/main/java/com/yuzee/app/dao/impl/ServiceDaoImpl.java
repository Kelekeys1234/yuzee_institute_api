package com.yuzee.app.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.repository.ServiceRepository;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.support.PageableExecutionUtils;

@org.springframework.stereotype.Service
@Slf4j
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private MessageTranslator messageTranslator;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Service> addUpdateServices(List<Service> services) throws ValidationException {
		try {
			return serviceRepository.saveAll(services);
		} catch (DataIntegrityViolationException ex) {
			log.error(messageTranslator.toLocale("services.already.exist", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("services.already.exist"));
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
		org.springframework.data.mongodb.core.query.Query mongoQuery = new org.springframework.data.mongodb.core.query.Query();
		mongoQuery.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("id").in(ids));
		return mongoTemplate.find(mongoQuery, Service.class, "service");

	}

	@Override
	public List<Service> findByNameIgnoreCaseIn(List<String> names) {
		return serviceRepository.findByNameIgnoreCaseIn(names);
	}

}
