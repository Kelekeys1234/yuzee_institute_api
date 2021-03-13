package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseEnglishEligibility;
import com.yuzee.app.dao.CourseEnglishEligibilityDao;
import com.yuzee.app.repository.CourseEnglishEligibilityRepository;

@Component
public class CourseEnglishEligibilityDaoImpl implements CourseEnglishEligibilityDao {

	@Autowired
	private CourseEnglishEligibilityRepository courseEnglishEligibilityRepository;

	@Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(final String courseID) {
		return courseEnglishEligibilityRepository.findByCourseId(courseID);
	}
}
