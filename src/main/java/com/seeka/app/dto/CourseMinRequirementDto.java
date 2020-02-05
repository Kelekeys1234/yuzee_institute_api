package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class CourseMinRequirementDto {

	private String country;
	private String system;
	private List<String> subject;
	private List<String> grade;
	private String course;

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(final String system) {
		this.system = system;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(final List<String> subject) {
		this.subject = subject;
	}

	public List<String> getGrade() {
		return grade;
	}

	public void setGrade(final List<String> grade) {
		this.grade = grade;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(final String course) {
		this.course = course;
	}

}
