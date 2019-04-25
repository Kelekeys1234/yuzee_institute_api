package com.seeka.app.dto;

public class CourseSearchFilterDto {
	
	private String price;
	private String duration;
	private String location;
	private String recognition;
	private String latestCourse;
	private String institute;
	private String course;
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	public String getLatestCourse() {
		return latestCourse;
	}
	public void setLatestCourse(String latestCourse) {
		this.latestCourse = latestCourse;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	} 
	 
}
