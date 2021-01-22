package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.dao.CourseFundingDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseFundingRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CourseFundingDaoImpl implements CourseFundingDao {

	@Autowired
	private CourseFundingRepository courseFundingRepository;

	@Override
	public List<CourseFunding> saveAll(List<CourseFunding> courseFundings) throws ValidationException {
		try {
			return courseFundingRepository.saveAll(courseFundings);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course funding already exists");
			throw new ValidationException("one or more course funding already exists");
		}
	}

}
