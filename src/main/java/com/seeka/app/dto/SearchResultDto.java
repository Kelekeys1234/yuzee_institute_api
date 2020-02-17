package com.seeka.app.dto;import java.util.List;

public class SearchResultDto {
	
	private List<CourseDto> courseList;

	public List<CourseDto> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseDto> courseList) {
		this.courseList = courseList;
	}
	 
}
