package com.yuzee.app.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.GlobalData;
import com.yuzee.app.repository.GlobalDataRepository;

@Repository
public class GlobalStudentDataDAO implements IGlobalStudentDataDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private MongoOperations mongoOperation;
	
	@Autowired
	private GlobalDataRepository globalDataRepository;

	@Override
	public void save(final GlobalData globalDataDato) {
		globalDataRepository.save(globalDataDato);
	}

	@Override
	public void deleteAll() {
		globalDataRepository.deleteAll();
	}

	@Override
	public List<GlobalData> getCountryWiseStudentList(final String countryName) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("sourceCountry").is(countryName));
		return mongoTemplate.find(query, GlobalData.class,"global_student_data");
	}

	@Override
	public long getNonZeroCountOfStudentsForCountry(final String countryName) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("sourceCountry").is(countryName));
		return (long) mongoOperation.count(query, GlobalData.class ,"global_student_data");
	}

	@Override
	public List<String> getDistinctMigratedCountryForStudentCountry(final String countryName) {
		Query query = new Query();
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("sourceCountry").is(countryName));
		List<GlobalData> globalList=mongoTemplate.find(query, GlobalData.class,"global_student_data");
		String list= globalList.stream().map(e->e.getSourceCountry()).toString();
		List<String> listGlobal = Arrays.asList(list);
		return listGlobal ;
	}

	@Override
	public List<String> getDistinctMigratedCountryForStudentCountryOrderByNumberOfStudents(final String countryName) {

		Query query = new Query();
		query.with(Sort.by(Sort.Direction.ASC,"totalNumberOfStudent"));
		query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("sourceCountry").is(countryName));
		List<GlobalData> globalList=mongoTemplate.find(query, GlobalData.class,"global_student_data");
		String list= globalList.stream().map(e->e.getSourceCountry()).toString();
		List<String> listGlobal = Arrays.asList(list);
		return listGlobal;
	}

	@Override
	public List<GlobalData> findAll() {
		return globalDataRepository.findAll();
	}
	
	@Override
	public void saveAll(List<GlobalData> list) {
		globalDataRepository.saveAll(list);
	}
}
