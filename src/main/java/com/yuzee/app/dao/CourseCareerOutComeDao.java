package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseCareerOutcome;
import com.yuzee.app.exception.ValidationException;

public interface CourseCareerOutComeDao {

	List<CourseCareerOutcome> saveAll(List<CourseCareerOutcome> courseCareerOutcomes) throws ValidationException;

	List<CourseCareerOutcome> findByCourseIdAndIdIn(String courseId, List<String> ids);

	void deleteByCourseIdAndIdIn(String courseId, List<String> ids);
}
