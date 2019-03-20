package com.seeka.app.dto;

import com.seeka.app.bean.Course;
import com.seeka.app.bean.Institute;

public class CourseDto {
	
	private Course courseObj;
	private Institute instituteObj;
	
	public Course getCourseObj() {
		return courseObj;
	}
	public void setCourseObj(Course courseObj) {
		this.courseObj = courseObj;
	}
	public Institute getInstituteObj() {
		return instituteObj;
	}
	public void setInstituteObj(Institute instituteObj) {
		this.instituteObj = instituteObj;
	}
	  
	 
}
