package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.dao.CourseDeliveryModesDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.CourseDeliveryModesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseDeliveryModesDaoImpl implements CourseDeliveryModesDao {

	@Autowired
	private CourseDeliveryModesRepository courseDeliveryModesRepository;

	@Override
	public List<CourseDeliveryModes> saveAll(List<CourseDeliveryModes> courseDeliveryModes) throws ValidationException {
		try {
			return courseDeliveryModesRepository.saveAll(courseDeliveryModes);
		} catch (DataIntegrityViolationException e) {
			log.error("one or more course delivery mode already exists with same delivert_type and study_mode");
			throw new ValidationException(
					"one or more course delivery mode already exists with same delivert_type and study_mode");
		}
	}

	@Override
	public List<CourseDeliveryModes> getCourseDeliveryModesByCourseId(String courseId) {
		return courseDeliveryModesRepository.findByCourseId(courseId);
	}

	@Override
	public void deleteByIdIn(List<String> ids) {
		courseDeliveryModesRepository.deleteByIdIn(ids);
	}

	@Override
	public List<CourseDeliveryModes> findByIdIn(List<String> ids) {
		return courseDeliveryModesRepository.findAllById(ids);
	}
}
