package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Careers;
import com.yuzee.app.dao.CareerDao;
import com.yuzee.app.repository.CareerRepository;

@Component
public class CareerDaoImpl implements CareerDao {

	@Autowired
	private CareerRepository careerRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Page<Careers> findByNameContainingIgnoreCase(String name, Pageable pageable) {
		return careerRepository.findByCareerContainingIgnoreCase(name, pageable);
	}

	@Override
	public List<Careers> findByIdIn(List<String> ids) {
		Query query = new Query();
		for(String id : ids) {
		query.addCriteria(Criteria.where("id").is(id));
		}
		return mongoTemplate.find(query, Careers.class,"career_list");
		
	}
}
