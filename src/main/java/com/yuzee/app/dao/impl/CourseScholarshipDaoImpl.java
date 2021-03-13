package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseScholarship;
import com.yuzee.app.dao.CourseScholarshipDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseScholarshipRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourseScholarshipDaoImpl implements CourseScholarshipDao {

	@Autowired
	private CourseScholarshipRepository courseScholarshipRepository;

	@Override
	public CourseScholarship save(CourseScholarship courseScholarship) throws ValidationException {
		try {
			return courseScholarshipRepository.save(courseScholarship);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course scholarships already exists with same scholarship_id");
			throw new ValidationException("one or more course scholarships already exists with same scholarship_id");
		}
	}

	@Override
	public CourseScholarship findByCourseId(String courseId) {
		return courseScholarshipRepository.findByCourseId(courseId);
	}
}
