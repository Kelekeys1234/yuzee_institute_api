package com.seeka.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.CourseMinRequirement;
import com.seeka.app.dao.CourseMinRequirementDao;
import com.seeka.app.repository.CourseMinimumRequirementsRepository;

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
