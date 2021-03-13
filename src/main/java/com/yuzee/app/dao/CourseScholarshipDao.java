package com.yuzee.app.dao;

import com.yuzee.app.bean.CourseScholarship;
import com.yuzee.app.exception.ValidationException;

public interface CourseScholarshipDao {

	CourseScholarship save(CourseScholarship courseScholarships) throws ValidationException;

	CourseScholarship findByCourseId(String courseId);
}
