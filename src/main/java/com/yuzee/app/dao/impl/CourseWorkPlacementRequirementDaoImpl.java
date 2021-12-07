package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.dao.CourseWorkPlacementRequirementDao;
import com.yuzee.app.repository.CourseWorkPlacementRequirementRepository;

@Component
public class CourseWorkPlacementRequirementDaoImpl implements CourseWorkPlacementRequirementDao {

	@Autowired
	private CourseWorkPlacementRequirementRepository repository;

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

}
