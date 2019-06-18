package com.seeka.app.dto;import java.math.BigInteger;

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
	
	public BigInteger getCourseId() {
		return courseId;
	}
	public void setCourseId(BigInteger courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseLanguage() {
		return courseLanguage;
	}
	public void setCourseLanguage(String courseLanguage) {
		this.courseLanguage = courseLanguage;
	}
	public String getLanguageShortKey() {
		return languageShortKey;
	}
	public void setLanguageShortKey(String languageShortKey) {
		this.languageShortKey = languageShortKey;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}
	public String getLocalFees() {
		return localFees;
	}
	public void setLocalFees(String localFees) {
		this.localFees = localFees;
	}
	public String getIntlFees() {
		return intlFees;
	}
	public void setIntlFees(String intlFees) {
		this.intlFees = intlFees;
	}
	public String getWorldRanking() {
		return worldRanking;
	}
	public void setWorldRanking(String worldRanking) {
		this.worldRanking = worldRanking;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getCostOfLiving() {
		return costOfLiving;
	}
	public void setCostOfLiving(String costOfLiving) {
		this.costOfLiving = costOfLiving;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDelivery() {
		if(null == delivery) {
			delivery = "";
		}
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getIntakeDate() {
		if(null == intakeDate) {
			intakeDate = "";
		}
		return intakeDate;
	}
	public void setIntakeDate(String intakeDate) {
		this.intakeDate = intakeDate;
	}
	public BigInteger getLevelId() {
		return levelId;
	}
	public void setLevelId(BigInteger levelId) {
		this.levelId = levelId;
	}
	
}
