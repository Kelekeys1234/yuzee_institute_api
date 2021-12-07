package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.dao.CourseWorkExperienceRequirementDao;
import com.yuzee.app.repository.CourseWorkExperienceRequirementRepository;

@Component
public class CourseWorkExperienceRequirementDaoImpl implements CourseWorkExperienceRequirementDao {

	@Autowired
	private CourseWorkExperienceRequirementRepository repository;

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

}
