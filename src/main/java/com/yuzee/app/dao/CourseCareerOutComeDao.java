package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseCareerOutcome;

public interface CourseCareerOutComeDao {

	public List<CourseCareerOutcome> getCourseCareerOutcome(String courseId);
}
