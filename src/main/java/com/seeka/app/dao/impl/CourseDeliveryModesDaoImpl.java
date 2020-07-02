package com.seeka.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.CourseDeliveryModes;
import com.seeka.app.dao.CourseDeliveryModesDao;
import com.seeka.app.repository.CourseDeliveryModesRepository;

@Component
public class CourseDeliveryModesDaoImpl implements CourseDeliveryModesDao {

	@Autowired
	private CourseDeliveryModesRepository courseAdditionalInfoRepository;

	@Override
	public void saveCourseAdditionalInfo(CourseDeliveryModes courseAdditionalInfo) {
		courseAdditionalInfoRepository.save(courseAdditionalInfo);
	}

	@Override
	public void deleteCourseAdditionalInfo(CourseDeliveryModes courseAdditionalInfo) {
		courseAdditionalInfoRepository.delete(courseAdditionalInfo);
	}

	@Override
	public List<CourseDeliveryModes> getCourseAdditionalInfoByCourseId(String courseId) {
		return courseAdditionalInfoRepository.findByCourseId(courseId);
	}
}
