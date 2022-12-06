package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.dao.InstituteServiceDao;
import com.yuzee.app.repository.InstituteServiceRepository;
import com.yuzee.common.lib.dto.CountDto;

@Service
public class InstituteServiceDaoImpl implements InstituteServiceDao {

	@Autowired
	private InstituteServiceRepository instituteServiceRepository;
	@Autowired
	private MongoOperations operator;

	@Override
	public Optional<InstituteService> get(String id) {
		return instituteServiceRepository.findById(id);
	}

	@Override
	public List<InstituteService> getAllInstituteService(String instituteId) {
		return instituteServiceRepository.findByInstituteId(instituteId);
	}

	@Override
	public List<InstituteService> saveAll(List<InstituteService> listOfInstituteService) {
		return instituteServiceRepository.saveAll(listOfInstituteService);
	}

	@Transactional
	@Override
	public void delete(String instituteServiceId) {
		instituteServiceRepository.deleteById(instituteServiceId);
	}

	@Override
	public List<CountDto> countByInstituteIds(List<String> instituteIds) {
		Query mongoQuery = new Query();
		mongoQuery.addCriteria(Criteria.where("id").in(instituteIds));
		List<CountDto> count= operator.find(mongoQuery, CountDto.class);
		return count;
	}
}