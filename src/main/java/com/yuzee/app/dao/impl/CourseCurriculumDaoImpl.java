package com.yuzee.app.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseCurriculum;
import com.yuzee.app.dao.CourseCurriculumDao;
import com.yuzee.app.repository.CourseCurriculumRepository;

@Component
public class CourseCurriculumDaoImpl implements CourseCurriculumDao {

	@Autowired
	private CourseCurriculumRepository courseCurriculumRespository;

	@Override
	public Optional<CourseCurriculum> getById(String id) {
		return courseCurriculumRespository.findById(id);
	}

	@Override
	public List<CourseCurriculum> getAll() {
		return courseCurriculumRespository.findAll();
	}

}
