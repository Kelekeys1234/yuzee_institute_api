package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.InstituteCampus;
import com.yuzee.app.dao.InstituteCampusDao;
import com.yuzee.app.repository.InstituteCampusRepository;

@Service
@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class InstituteCampusDaoImpl implements InstituteCampusDao {

	@Autowired
	private InstituteCampusRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<InstituteCampus> saveAll(List<InstituteCampus> entities) {
		return repository.saveAll(entities);
	}

	@Override
	public List<InstituteCampus> findInstituteCampuses(UUID instituteId) {
		Query mongoQuery = new Query();
		mongoQuery.addCriteria(Criteria.where("id").is(instituteId));
		return mongoTemplate.find(mongoQuery, InstituteCampus.class, "institute");
	}

	@Override
	public void deleteAll(List<InstituteCampus> courseInstitutes) {
		repository.deleteAll(courseInstitutes);
	}
}
