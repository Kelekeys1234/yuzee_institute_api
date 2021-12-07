package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.dao.CourseIntakeDao;
import com.yuzee.app.repository.CourseIntakeRepository;

@Component
public class CourseIntakeDaoImpl implements CourseIntakeDao {

	@Autowired
	private CourseIntakeRepository courseIntakeRepository;

	@Transactional
	@Override
	public void deleteByCourseIdIn(List<String> courseIds) {
		courseIntakeRepository.deleteByCourseIdIn(courseIds);
	}
}
