package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.dao.CourseVaccineRequirementDao;
import com.yuzee.app.repository.CourseVaccineRequirementRepository;

@Component
public class CourseVaccineRequirementDaoImpl implements CourseVaccineRequirementDao {

	@Autowired
	private CourseVaccineRequirementRepository repository;

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

}
