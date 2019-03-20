package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
		
	@ManyToOne
	@JoinColumn(name="institute_id")
	private Institute instituteObj;//InstituteId
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country countryObj;//CountryId
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City cityObj;//CityId
	
	@ManyToOne
	@JoinColumn(name = "faculty_id")
	private Faculty facultyObj; //Faculty Object
	
	@Column(name="name")
	private String name; // name
	
	@Column(name="world_ranking")
	private Integer worldRanking; // World Ranking
	
	@Column(name="stars")
	private Integer stars; // Stars
	
	@Column(name="recognition")
	private String recognition; // Recognition
	
	@Column(name="recognition_type")
	private String recognitionType; // Recognition Type
	
	@Column(name="duration")
	private String duration; // Duration
	
	@Column(name="duration_time")
	private String durationTime; // Duration Time
	
	@Column(name="website")
	private String website; // Website
	
	@Column(name="course_lang")
	private String courseLanguage; // Course Language
	
	@Column(name="abbreviation")
	private String abbreviation; // Abbreviation
	
	@Column(name="rec_date")
	private Date recordedDate; // Recorded Date
	
	@Column(name="remarks")
	private String remarks; // Abbreviation
	
	@Column(name="description")
	private String description; // Description
	
	@Column(name="is_active")
	private	Boolean isActive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Institute getInstituteObj() {
		return instituteObj;
	}

	public void setInstituteObj(Institute instituteObj) {
		this.instituteObj = instituteObj;
	}

	public Country getCountryObj() {
		return countryObj;
	}

	public void setCountryObj(Country countryObj) {
		this.countryObj = countryObj;
	}

	public City getCityObj() {
		return cityObj;
	}

	public void setCityObj(City cityObj) {
		this.cityObj = cityObj;
	}

	public Faculty getFacultyObj() {
		return facultyObj;
	}

	public void setFacultyObj(Faculty facultyObj) {
		this.facultyObj = facultyObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(Integer worldRanking) {
		this.worldRanking = worldRanking;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getRecognitionType() {
		return recognitionType;
	}

	public void setRecognitionType(String recognitionType) {
		this.recognitionType = recognitionType;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCourseLanguage() {
		return courseLanguage;
	}

	public void setCourseLanguage(String courseLanguage) {
		this.courseLanguage = courseLanguage;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	 
  
}
