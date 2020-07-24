package com.yuzee.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.CoursePrerequisite;
import com.yuzee.app.bean.CoursePrerequisiteSubjects;
import com.yuzee.app.dao.CoursePrerequisiteDao;
import com.yuzee.app.repository.CoursePrerequisiteRepository;
import com.yuzee.app.repository.CoursePrerequisiteSubjectRepository;

@Component
public class CoursePrerequisiteDaoImpl implements CoursePrerequisiteDao {

	@Autowired
	private CoursePrerequisiteRepository coursePrerequisiteRepository;
	
	@Autowired
	private CoursePrerequisiteSubjectRepository coursePrerequisiteSubjectRepository;

	@Override
	public List<CoursePrerequisite> getCoursePrerequisite(String courseId) {
		return coursePrerequisiteRepository.findByCourseId(courseId);
	}

	@Override
	public List<CoursePrerequisiteSubjects> getCoursePrerequisiteSubjects(String coursePrerequisiteId) {
		return coursePrerequisiteSubjectRepository.findByCoursePrerequisiteId(coursePrerequisiteId);
	}
}
