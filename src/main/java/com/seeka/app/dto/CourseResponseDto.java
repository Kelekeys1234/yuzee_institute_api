package com.seeka.app.dto;

import java.util.UUID;

public class CourseResponseDto {
	
	private UUID courseId;
	private String courseName;	
	private String courseLanguage;
	private String languageShortKey;
	private UUID instituteId;
	private String instituteName;
	private String instituteLogoUrl;	
	private String instituteImageUrl;	
	private String worldRanking;	
	private String stars;
	private String cost;
	private String duration;
	private String durationTime;
	private String location;
	private UUID countryId;
	private UUID cityId;
	private Integer totalCount;
	private String localFees;
	private String intlFees;
	private String requirements;
	
	public UUID getCourseId() {
		return courseId;
	}
	public void setCourseId(UUID courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public UUID getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(UUID instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getInstituteLogoUrl() {
		return instituteLogoUrl;
	}
	public void setInstituteLogoUrl(String instituteLogoUrl) {
		this.instituteLogoUrl = instituteLogoUrl;
	}
	public String getInstituteImageUrl() {
		return instituteImageUrl;
	}
	public void setInstituteImageUrl(String instituteImageUrl) {
		this.instituteImageUrl = instituteImageUrl;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public UUID getCountryId() {
		return countryId;
	}
	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}
	public UUID getCityId() {
		return cityId;
	}
	public void setCityId(UUID cityId) {
		this.cityId = cityId;
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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
	public String getRequirements() {
		if(null == requirements || requirements.isEmpty() || requirements.contains("0.0") || requirements.contains("0.00")) {
			requirements = "No Requirements";
		}
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	
}
