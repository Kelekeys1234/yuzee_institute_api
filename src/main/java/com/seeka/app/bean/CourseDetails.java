package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="course_details")
public class CourseDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="course_id")
	private UUID courseId;
		
	@Column(name="wr_range")
	private String worldRange;//World Range
	
	@Column(name="availbilty")
	private String availbilty; // Availbilty
	
	@Column(name="part_full")
	private String partFull;//Part Full
	
	@Column(name="study_mode")
	private String studyMode;//Study Mode
	
	@Column(name="description")
	private String description; // Description
	
	@Transient
	private Course courseObj;
	
	@Transient
	private CoursePricing coursePricingObj;
	
	@Transient
	private Faculty facultyObj;
	
	@Transient
	private Level LevelObj;
	
	@Transient
	private FacultyLevel facultyLevelObj;
	
	@Transient
	private InstituteLevel instituteLevelObj;
 
	 

	public UUID getCourseId() {
		return courseId;
	}



	public void setCourseId(UUID courseId) {
		this.courseId = courseId;
	}



	public String getWorldRange() {
		return worldRange;
	}



	public void setWorldRange(String worldRange) {
		this.worldRange = worldRange;
	}



	public String getAvailbilty() {
		return availbilty;
	}



	public void setAvailbilty(String availbilty) {
		this.availbilty = availbilty;
	}



	public String getPartFull() {
		return partFull;
	}



	public void setPartFull(String partFull) {
		this.partFull = partFull;
	}



	public String getStudyMode() {
		return studyMode;
	}



	public void setStudyMode(String studyMode) {
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



	public CoursePricing getCoursePricingObj() {
		return coursePricingObj;
	}



	public void setCoursePricingObj(CoursePricing coursePricingObj) {
		this.coursePricingObj = coursePricingObj;
	}



	public Faculty getFacultyObj() {
		return facultyObj;
	}



	public void setFacultyObj(Faculty facultyObj) {
		this.facultyObj = facultyObj;
	}



	public Level getLevelObj() {
		return LevelObj;
	}



	public void setLevelObj(Level levelObj) {
		LevelObj = levelObj;
	}



	public FacultyLevel getFacultyLevelObj() {
		return facultyLevelObj;
	}



	public void setFacultyLevelObj(FacultyLevel facultyLevelObj) {
		this.facultyLevelObj = facultyLevelObj;
	}



	public InstituteLevel getInstituteLevelObj() {
		return instituteLevelObj;
	}


	public void setInstituteLevelObj(InstituteLevel instituteLevelObj) {
		this.instituteLevelObj = instituteLevelObj;
	}


}
