package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.CourseSubject;
import com.yuzee.app.dao.CourseSubjectDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseSubjectRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourseSubjectDaoImpl implements CourseSubjectDao {

	@Autowired
	private CourseSubjectRepository courseSubjectRepository;

	@Override
	public List<CourseSubject> saveAll(List<CourseSubject> courseSubjects) throws ValidationException {
		try {
			return courseSubjectRepository.saveAll(courseSubjects);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course subject already exists with same name and semester");
			throw new ValidationException("one or more course subject already exists with same name and semester");
		}
	}

	@Override
	public List<CourseSubject> findByCourseIdAndIdIn(String courseId, List<String> ids) {
		return courseSubjectRepository.findByCourseIdAndIdIn(courseId, ids);
	}

	@Transactional
	@Override
	public void deleteByCourseIdAndIdIn(String courseId, List<String> ids) {
		courseSubjectRepository.deleteByCourseIdAndIdIn(courseId, ids);
	}
}
