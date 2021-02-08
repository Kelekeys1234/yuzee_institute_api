package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CourseContactPerson;
import com.yuzee.app.exception.ValidationException;

public interface CourseContactPersonDao {
	public List<CourseContactPerson> saveAll(List<CourseContactPerson> courseContactPersons) throws ValidationException;

	public List<CourseContactPerson> findByCourseIdAndUserIdIn(String courseId, List<String> userIds);

	void deleteByCourseIdAndUserIdIn(String courseId, List<String> userIds);
}
