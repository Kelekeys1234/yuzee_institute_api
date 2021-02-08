package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.dao.CourseEnglishEligibilityDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseEnglishEligibilityRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourseEnglishEligibilityDaoImpl implements CourseEnglishEligibilityDao {

	@Autowired
	private CourseEnglishEligibilityRepository courseEnglishEligibilityRepository;

	@Override
	public List<CourseEnglishEligibility> saveAll(List<CourseEnglishEligibility> courseSubjects)
			throws ValidationException {
		try {
			return courseEnglishEligibilityRepository.saveAll(courseSubjects);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course english eligibiliy already exists with same english_type");
			throw new ValidationException(
					"one or more course english eligibiliy already exists with same english_type");
		}
	}

	@Override
	public List<CourseEnglishEligibility> findByCourseIdAndIdIn(String courseId, List<String> ids) {
		return courseEnglishEligibilityRepository.findByCourseIdAndIdIn(courseId, ids);
	}

	@Transactional
	@Override
	public void deleteByCourseIdAndIdIn(String courseId, List<String> ids) {
		courseEnglishEligibilityRepository.deleteByCourseIdAndIdIn(courseId, ids);
	}

	@Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(final String courseID) {
		return courseEnglishEligibilityRepository.findByCourseIdAndIsActive(courseID, true);
	}
}
