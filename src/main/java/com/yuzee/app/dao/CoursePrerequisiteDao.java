package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.CoursePrerequisite;
import com.yuzee.app.bean.CoursePrerequisiteSubjects;

public interface CoursePrerequisiteDao {

	public List<CoursePrerequisite> getCoursePrerequisite(String courseId);
	
	public List<CoursePrerequisiteSubjects> getCoursePrerequisiteSubjects(String coursePrerequisiteId);
}
