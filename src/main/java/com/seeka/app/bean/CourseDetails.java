package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;

@Entity
@Table(name="course_details")
public class CourseDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="course_id")
	private Integer courseId;
		
	@Column(name="wr_range")
	private Integer worldRange;//World Range
	
	@Column(name="availbilty")
	private String availbilty; // Availbilty
	
	@Column(name="part_full")
	private Integer partFull;//Part Full
	
	@Column(name="study_mode")
	private Integer studyMode;//Study Mode
	
	@Column(name="description")
	private String description; // Description
	
	@Transient
	private Course courseObj;
 
	 
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getWorldRange() {
		return worldRange;
	}

	public void setWorldRange(Integer worldRange) {
		this.worldRange = worldRange;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
	 
	public Course getCourseObj() {
		return courseObj;
	}

	public void setCourseObj(Course courseObj) {
		this.courseObj = courseObj;
	}

	public static void main(String[] args) {
		
		CourseDetails courseDetails = new CourseDetails();
		Course course = new Course();
		
		City cityObj = new City();
		cityObj.setId(4);
		
		Country countryObj = new Country();
		countryObj.setId(257);
		
		Faculty facultyObj = new Faculty();
		facultyObj.setId(1);
		
		Institute instituteObj = new Institute();
		instituteObj.setId(3);
		
		course.setAbbreviation("AU,INDIAN,ECOMMERCE");
		course.setCityObj(cityObj);
		course.setCountryObj(countryObj);
		course.setCourseLanguage("English");
		course.setDescription("Information about the course");
		course.setCreatedBy("Ulaga");
		course.setDuration("4 Years");
		course.setDurationTime("May - Sep");
		course.setFacultyObj(facultyObj);
		
		course.setInstituteObj(instituteObj);
		course.setIsActive(true);
		course.setIsDeleted(false);
		course.setName("Computer Science and Engineering");
		course.setRecognition("Recognition");
		course.setRecognitionType("Recognition Type");
		course.setRecordedDate(new Date());
		course.setRemarks("Course Remarks");
		course.setStars(4);
		course.setWebsite("www.coursewebsite.com");
		course.setWorldRanking(250);
		
		courseDetails.setAvailbilty("24/7");
		courseDetails.setCourseObj(course);
		courseDetails.setDescription("Course Detail Description");
		courseDetails.setPartFull(1);
		courseDetails.setStudyMode(1);
		courseDetails.setWorldRange(1);
		
		Gson gson = new Gson();
		
		System.out.println(gson.toJson(courseDetails));
		
		System.out.println(new Date().getTime());
	}
    
	
  
}
