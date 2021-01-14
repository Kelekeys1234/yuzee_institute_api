package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.exception.ValidationException;

public interface CourseIntakeDao {
	List<CourseIntake> saveAll(List<CourseIntake> courseIntakes) throws ValidationException;

	List<CourseIntake> findByIdIn(List<String> ids);

	void deleteByIdIn(List<String> ids);
}