package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.dao.CourseIntakeDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseIntakeRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourseIntakeDaoImpl implements CourseIntakeDao {

	@Autowired
	private CourseIntakeRepository courseIntakeRepository;

	@Override
	public List<CourseIntake> saveAll(List<CourseIntake> courseIntakes) throws ValidationException {
		try {
			return courseIntakeRepository.saveAll(courseIntakes);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course intake already exists with same intake date");
			throw new ValidationException("one or more course intake already exists with same intake date");
		}
	}

	@Override
	public List<CourseIntake> findByCourseIdAndIdIn(String courseId, List<String> ids) {
		return courseIntakeRepository.findByCourseIdAndIdIn(courseId, ids);
	}

	@Transactional
	@Override
	public void deleteByCourseIdAndIdIn(String courseId, List<String> ids) {
		courseIntakeRepository.deleteByCourseIdAndIdIn(courseId, ids);
	}
}
