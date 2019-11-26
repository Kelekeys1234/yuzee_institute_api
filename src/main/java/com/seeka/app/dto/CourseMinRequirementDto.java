package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class CourseMinRequirementDto {

	private BigInteger country;
	private BigInteger system;
	private List<String> subject;
	private List<String> grade;
	private BigInteger course;

	public BigInteger getCountry() {
		return country;
	}

	public void setCountry(final BigInteger country) {
		this.country = country;
	}

	public BigInteger getSystem() {
		return system;
	}

	public void setSystem(final BigInteger system) {
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

	public BigInteger getCourse() {
		return course;
	}

	public void setCourse(final BigInteger course) {
		this.course = course;
	}

}
