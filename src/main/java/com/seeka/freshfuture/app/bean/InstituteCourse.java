package com.seeka.freshfuture.app.bean;

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
@Table(name="institute_course")
public class InstituteCourse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="course_id")
	private Course courseObj; //Course Details
	
	@ManyToOne
	@JoinColumn(name="institute_id")
	private Institute instituteObj; //Institute Details
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country countryObj; //Country Detail
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City cityObj; //City Detail
	
	@Column(name="world_ranking")
	private Integer worldRanking; //World Ranking
	
	@Column(name="recognition")
	private String recognition; //Recognition
	
	@Column(name="recognition_type")
	private String recognitionType; //Recognition Type
	
	@Column(name="duration")
	private String duration; //Course Duration
	
	@Column(name="duration_time")
	private String durationTime; //Course Duration Time
	
	@Column(name="website")
	private String website; //Websites	 
	
	@Column(name="course_lang")
	private String courseLang; //Course language
	
	@Column(name="description")
	private String description; //Course Description
	
	@Column(name="abbreviation")
	private String abbreviation; //Course Abbreviation
	
	@Column(name="availbilty")
	private String availbilty; //Course Availability
	 
	@Column(name="part_full")
	private Integer partFull; //City
	
	@Column(name="study_mode")
	private Integer studyMode; //City
	
	@Column(name="stars")
	private Integer stars; //City
	
	@Column(name="wr_range")
	private Integer wrRange; //Wr Range
	
	@Column(name="rec_date")
	private Date recDate; //Rec Date
	 
	@Column(name="remarks")
	private String remarks; //Remarks

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getWorldRanking() {
		return worldRanking;
	}

	public void setWorldRanking(Integer worldRanking) {
		this.worldRanking = worldRanking;
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

	public String getCourseLang() {
		return courseLang;
	}

	public void setCourseLang(String courseLang) {
		this.courseLang = courseLang;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAvailbilty() {
		return availbilty;
	}

	public void setAvailbilty(String availbilty) {
		this.availbilty = availbilty;
	}

	public Integer getPartFull() {
		return partFull;
	}

	public void setPartFull(Integer partFull) {
		this.partFull = partFull;
	}

	public Integer getStudyMode() {
		return studyMode;
	}

	public void setStudyMode(Integer studyMode) {
		this.studyMode = studyMode;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getWrRange() {
		return wrRange;
	}

	public void setWrRange(Integer wrRange) {
		this.wrRange = wrRange;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
 
}
