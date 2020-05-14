package com.seeka.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seeka.app.bean.CourseGradeEligibility;
import com.seeka.app.repository.CourseGradeEligibilityRepository;

@Component
public class CourseGradeEligibilityDAO implements ICourseGradeEligibilityDAO {
	
	@Autowired
	private CourseGradeEligibilityRepository courseGradeEligibilityRepository;


	@Override
	public void save(final CourseGradeEligibility obj) {
		courseGradeEligibilityRepository.save(obj);
	}

	@Override
	public void update(final CourseGradeEligibility obj) {
		courseGradeEligibilityRepository.save(obj);
	}

	@Override
	public Optional<CourseGradeEligibility> get(final String id) {
		return courseGradeEligibilityRepository.findById(id);
	}

	@Override
	public List<CourseGradeEligibility> getAll() {
		return courseGradeEligibilityRepository.findAll();
	}

	@Override
	public CourseGradeEligibility getCourseGradeEligibilityByCourseId(final String courseId) {
		return courseGradeEligibilityRepository.findByCourseId(courseId);
	}

}
