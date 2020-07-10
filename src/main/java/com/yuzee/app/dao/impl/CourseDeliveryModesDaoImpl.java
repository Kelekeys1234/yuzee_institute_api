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
