package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.CourseContactPerson;
import com.yuzee.app.dao.CourseContactPersonDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseContactPersonRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CourseContactPersonDaoImpl implements CourseContactPersonDao {

	@Autowired
	private CourseContactPersonRepository courseContactPersonRepository;

	@Override
	public List<CourseContactPerson> saveAll(List<CourseContactPerson> courseContactPersons)
			throws ValidationException {
		try {
			return courseContactPersonRepository.saveAll(courseContactPersons);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course contact person already exists");
			throw new ValidationException("one or more course contact person already exists");
		}
	}

	@Override
	public List<CourseContactPerson> findByCourseIdAndUserIdIn(String courseId, List<String> userIds) {
		return courseContactPersonRepository.findByCourseIdAndUserIdIn(courseId, userIds);
	}

	@Override
	public void deleteByCourseIdAndUserIdIn(String courseId, List<String> userIds) {
		courseContactPersonRepository.deleteByCourseIdAndUserIdIn(courseId, userIds);
	}

}
