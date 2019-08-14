package com.seeka.app.dto;

import java.math.BigInteger;

public class CourseDto {

	private BigInteger courseId;
	private BigInteger levelId;
	private String courseName;
	private String courseLanguage;
	private String languageShortKey;
	private String cost;
	private String duration;
	private String durationTime;
	private String localFees;
	private String intlFees;
	private String worldRanking;
	private String stars;
	private String facultyName;
	private String levelName;
	private String costOfLiving;
	private String description;
	private String delivery;
	private String intakeDate;
	private String remarks;

	public BigInteger getCourseId() {
		return courseId;
	}

	public void setCourseId(final BigInteger courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(final String courseName) {
		this.courseName = courseName;
	}

	public String getCourseLanguage() {
		return courseLanguage;
	}

	public void setCourseLanguage(final String courseLanguage) {
		this.courseLanguage = courseLanguage;
	}

	public String getLanguageShortKey() {
		return languageShortKey;
	}

	public void setLanguageShortKey(final String languageShortKey) {
		this.languageShortKey = languageShortKey;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(final String cost) {
		this.cost = cost;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(final String duration) {
		this.duration = duration;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(final String durationTime) {
		this.durationTime = durationTime;
	}

	public String getLocalFees() {
		return localFees;
	}

	public void setLocalFees(final String localFees) {
		this.localFees = localFees;
	}

	public String getIntlFees() {
		return intlFees;
	}

	public void setIntlFees(final String intlFees) {
		this.intlFees = intlFees;
	}

	public String getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(final String worldRanking) {
		this.worldRanking = worldRanking;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(final String stars) {
		this.stars = stars;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(final String facultyName) {
		this.facultyName = facultyName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(final String levelName) {
		this.levelName = levelName;
	}

	public String getCostOfLiving() {
		return costOfLiving;
	}

	public void setCostOfLiving(final String costOfLiving) {
		this.costOfLiving = costOfLiving;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getDelivery() {
		if (null == delivery) {
			delivery = "";
		}
		return delivery;
	}

	public void setDelivery(final String delivery) {
		this.delivery = delivery;
	}

	public String getIntakeDate() {
		if (null == intakeDate) {
			intakeDate = "";
		}
		return intakeDate;
	}

	public void setIntakeDate(final String intakeDate) {
		this.intakeDate = intakeDate;
	}

	public BigInteger getLevelId() {
		return levelId;
	}

	public void setLevelId(final BigInteger levelId) {
		this.levelId = levelId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

}
