package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CourseCareerOutcome;
import com.yuzee.app.dao.CourseCareerOutComeDao;
import com.yuzee.app.repository.CourseCareerOutComeRepository;

@Component
public class CourseCareerOutComeDaoImpl implements CourseCareerOutComeDao {

	@Autowired
	private CourseCareerOutComeRepository courseCareerOutComeRepository;
	
	@Override
	public List<CourseCareerOutcome> getCourseCareerOutcome(String courseId) {
		return courseCareerOutComeRepository.findByCourseId(courseId);
	}
}
