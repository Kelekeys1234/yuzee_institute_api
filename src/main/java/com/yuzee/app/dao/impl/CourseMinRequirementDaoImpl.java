package com.yuzee.app.dao.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseMinRequirement;
import com.yuzee.app.dao.CourseMinRequirementDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseMinimumRequirementsRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CourseMinRequirementDaoImpl implements CourseMinRequirementDao {

	private final static String ALREADY_EXISTS = "Course Minumum requirements with same country name, state name and education system id already exists  OR Course Minumum requirement subjects with same name already exists.";

	@Autowired
	private CourseMinimumRequirementsRepository courseMinRequirementRepository;

	@Override
	public CourseMinRequirement save(final CourseMinRequirement courseMinRequirement) throws ValidationException {
		try {
			return courseMinRequirementRepository.save(courseMinRequirement);
		} catch (DataIntegrityViolationException ex) {
			log.error(ALREADY_EXISTS);
			throw new ValidationException(ALREADY_EXISTS);
		}
	}

	@Override
	public Page<CourseMinRequirement> findByCourseId(final String courseId, Pageable pageable) {
		return courseMinRequirementRepository.findByCourseId(courseId, pageable);
	}

	@Override
	public void deleteByCourseIdAndId(String courseId, String id) {
		try {
			courseMinRequirementRepository.deleteByCourseIdAndId(courseId, id);
		} catch (EntityNotFoundException ex) {
			log.error("data not exists against courseId: {} and id: {}", courseId, id);
		}
	}

	@Override
	public CourseMinRequirement findByCourseIdAndId(String courseId, String id) {
		return courseMinRequirementRepository.findByCourseIdAndId(courseId, id);
	}

}
