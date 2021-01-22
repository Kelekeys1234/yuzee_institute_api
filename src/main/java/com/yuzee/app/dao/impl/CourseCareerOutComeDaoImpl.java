package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.CourseCareerOutcome;
import com.yuzee.app.dao.CourseCareerOutComeDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseCareerOutComeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseCareerOutComeDaoImpl implements CourseCareerOutComeDao {

	@Autowired
	private CourseCareerOutComeRepository courseCareerOutComeRepository;

	@Override
	public List<CourseCareerOutcome> saveAll(List<CourseCareerOutcome> courseCareerOutcomes)
			throws ValidationException {
		try {
			return courseCareerOutComeRepository.saveAll(courseCareerOutcomes);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course carrer already exists with same career_id");
			throw new ValidationException("one or more course carrer already exists with same career_id");
		}
	}

	@Override
	public List<CourseCareerOutcome> findByCourseIdAndIdIn(String courseId, List<String> ids) {
		return courseCareerOutComeRepository.findByCourseIdAndIdIn(courseId, ids);
	}

	@Transactional
	@Override
	public void deleteByCourseIdAndIdIn(String courseId, List<String> ids) {
		courseCareerOutComeRepository.deleteByCourseIdAndIdIn(courseId, ids);
	}
}
