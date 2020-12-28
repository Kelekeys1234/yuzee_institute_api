package com.yuzee.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.dao.CourseSubjectDao;
import com.yuzee.app.repository.CourseSubjectRepository;

@Service
public class CourseSubjectDaoImpl implements CourseSubjectDao {

	@Autowired
	private CourseSubjectRepository courseSubjectRepository;

	@Override
	public void deleteByCourseId(String courseId) {
		courseSubjectRepository.deleteByCourseId(courseId);
		courseSubjectRepository.flush();
	}

}
