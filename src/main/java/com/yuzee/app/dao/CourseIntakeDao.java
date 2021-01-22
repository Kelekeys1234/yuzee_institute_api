package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.exception.ValidationException;

public interface CourseIntakeDao {
	List<CourseIntake> saveAll(List<CourseIntake> courseIntakes) throws ValidationException;

	List<CourseIntake> findByCourseIdAndIdIn(String courseId, List<String> ids);

	void deleteByCourseIdAndIdIn(String courseId, List<String> ids);
}