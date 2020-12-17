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
	public void save(final CourseEnglishEligibility courseEnglishEligibility) {
		courseEnglishEligibilityRepository.save(courseEnglishEligibility);
	}

	@Override
	public void update(final CourseEnglishEligibility courseEnglishEligibility) {
		courseEnglishEligibilityRepository.save(courseEnglishEligibility);
	}

	@Override
	public CourseEnglishEligibility get(final String id) {
		return courseEnglishEligibilityRepository.findById(id).get();
	}

	@Override
	public List<CourseEnglishEligibility> getAll() {
		return courseEnglishEligibilityRepository.findAll();
	}

	@Override
	public List<CourseEnglishEligibility> getAllEnglishEligibilityByCourse(final String courseID) {
		return courseEnglishEligibilityRepository.findByCourseIdAndIsActive(courseID, true);
	}

	public void delete(final String courseID) {
		courseEnglishEligibilityRepository.findByCourseId(courseID);
	}

	@Override
	public void deleteCourseEnglishEligibilityByCourse(String courseId) {
		courseEnglishEligibilityRepository.deleteByCourseId(courseId);
		courseEnglishEligibilityRepository.flush();
	}
}
