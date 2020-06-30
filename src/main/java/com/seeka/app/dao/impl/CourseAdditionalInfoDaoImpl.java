package com.seeka.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.CourseAdditionalInfo;
import com.seeka.app.dao.CourseAdditionalInfoDao;
import com.seeka.app.repository.CourseAdditionalInfoRepository;

@Component
public class CourseAdditionalInfoDaoImpl implements CourseAdditionalInfoDao {

	@Autowired
	private CourseAdditionalInfoRepository courseAdditionalInfoRepository;

	@Override
	public void saveCourseAdditionalInfo(CourseAdditionalInfo courseAdditionalInfo) {
		courseAdditionalInfoRepository.save(courseAdditionalInfo);
	}

	@Override
	public void deleteCourseAdditionalInfo(CourseAdditionalInfo courseAdditionalInfo) {
		courseAdditionalInfoRepository.delete(courseAdditionalInfo);
	}

	@Override
	public List<CourseAdditionalInfo> getCourseAdditionalInfoByCourseId(String courseId) {
		return courseAdditionalInfoRepository.findByCourseId(courseId);
	}
}
