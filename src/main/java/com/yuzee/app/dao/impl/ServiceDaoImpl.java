package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
            log.error(messageTranslator.toLocale("services.already.exist",Locale.US));
            throw new ValidationException(messageTranslator.toLocale("services.already.exist"));
        }
    }

    @Override
    public Service getServiceById(String id) {
        Query mongoQuery = new Query();
        mongoQuery.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findById(id, Service.class);
    }

    @Override
    public Page<Service> getAllServices(Pageable pageable) {
        Query mongoQuery = new Query();
        mongoQuery.with(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        List<com.yuzee.app.bean.Service> list = mongoTemplate.find(mongoQuery, com.yuzee.app.bean.Service.class);
        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(mongoQuery).limit(-1).skip(-1), com.yuzee.app.bean.Service.class));
    }

    @Override
    public List<Service> getAll() {
        return mongoTemplate.findAll(Service.class);
    }

    @Override
    public List<Service> getAllByIds(List<String> ids) {
        Query mongoQuery = new Query();
        mongoQuery.addCriteria(Criteria.where("id").in(ids));
        return mongoTemplate.find(mongoQuery, Service.class, "service");
    }

    @Override
    public List<Service> findByNameIgnoreCaseIn(List<String> names) {
        Query mongoQuery = new Query();
        mongoQuery.addCriteria(Criteria.where("name").in(names));
        return mongoTemplate.find(mongoQuery, Service.class, "service");
    }

}
