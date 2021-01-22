package com.yuzee.app.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.CourseCurriculum;
import com.yuzee.app.dao.CourseCurriculumDao;
import com.yuzee.app.repository.CourseCurriculumRepository;

@Service
public class CourseCurriculumDaoImpl implements CourseCurriculumDao {

	@Autowired
	private CourseCurriculumRepository courseCurriculumRespository;

	@Override
	public Optional<CourseCurriculum> getById(String id) {
		return courseCurriculumRespository.findById(id);
	}

}
