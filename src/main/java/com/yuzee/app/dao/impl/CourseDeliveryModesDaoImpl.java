package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.dao.CourseDeliveryModesDao;
import com.yuzee.app.repository.CourseDeliveryModesRepository;

@Component
public class CourseDeliveryModesDaoImpl implements CourseDeliveryModesDao {

	@Autowired
	private CourseDeliveryModesRepository courseDeliveryModesRepository;

	@Override
	public List<CourseDeliveryModes> getCourseDeliveryModesByCourseId(String courseId) {
		return courseDeliveryModesRepository.findByCourseId(courseId);
	}
}
