package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseMinRequirement;
import com.yuzee.app.dao.CourseMinRequirementDao;
import com.yuzee.app.repository.CourseMinimumRequirementsRepository;

@Component
public class CourseMinRequirementDaoImpl implements CourseMinRequirementDao {

	@Autowired
	private CourseMinimumRequirementsRepository courseMinimumRequirementsRepository;

	@Override
	public void save(final CourseMinRequirement obj) {
		courseMinimumRequirementsRepository.save(obj);
	}

	@Override
	public List<CourseMinRequirement> getCourseMinRequirementByCourseId(final String courseId) {
		return courseMinimumRequirementsRepository.findByCourseId(courseId);
	}


}
