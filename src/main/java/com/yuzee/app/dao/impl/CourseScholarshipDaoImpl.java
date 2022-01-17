package com.yuzee.app.dao.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseScholarship;
import com.yuzee.app.dao.CourseScholarshipDao;
import com.yuzee.app.repository.CourseScholarshipRepository;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourseScholarshipDaoImpl implements CourseScholarshipDao {

	@Autowired
	private MessageTranslator messageTranslator;
	
	@Autowired
	private CourseScholarshipRepository courseScholarshipRepository;

	@Override
	public CourseScholarship save(CourseScholarship courseScholarship) throws ValidationException {
		try {
			return courseScholarshipRepository.save(courseScholarship);
		} catch (DataIntegrityViolationException e) {
			log.error(messageTranslator.toLocale("course-scolarship.already.scolarship_id.exist",Locale.US));
			throw new ValidationException(messageTranslator.toLocale("course-scolarship.already.scolarship_id.exist"));
		}
	}

	@Override
	public CourseScholarship findByCourseId(String courseId) {
		return courseScholarshipRepository.findByCourseId(courseId);
	}
}
