package com.seeka.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="course_details")
public class CourseDetails extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
		
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
	
	@Column(name="is_active")
	private	Boolean isActive;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
    
	
  
}
