package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseSubject;
import com.yuzee.app.exception.ValidationException;

public interface CourseSubjectDao {
	List<CourseSubject> saveAll(List<CourseSubject> courseSubjects) throws ValidationException;

	List<CourseSubject> findByIdIn(List<String> ids);

	void deleteByIdIn(List<String> ids);
}