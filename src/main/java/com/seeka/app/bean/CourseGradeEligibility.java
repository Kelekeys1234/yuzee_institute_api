package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="course_grade_eligibility")
public class CourseGradeEligibility extends RecordModifier implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "course_id", updatable = false, nullable = false)
	private UUID courseId;
	
	@Column(name="global_gpa")
	private Double globalGpa;  
	
	@Column(name="global_a_level_1")
	private String globalALevel1; 
	
	@Column(name="global_a_level_2")
	private String globalALevel2; 
	
	@Column(name="global_a_level_3")
	private String globalALevel3; 
	
	@Column(name="global_a_level_4")
	private String globalALevel4; 
	
	@Column(name="global_a_level_5")
	private String globalALevel5; 
	
	@Column(name="is_active")
	private	Boolean isActive;
	
	@Transient
	private String globalALevel; 

	public UUID getCourseId() {
		return courseId;
	}

	public void setCourseId(UUID courseId) {
		this.courseId = courseId;
	}

	public Double getGlobalGpa() {
		return globalGpa;
	}

	public void setGlobalGpa(Double globalGpa) {
		this.globalGpa = globalGpa;
	}

	public String getGlobalALevel1() {
		if(null == globalALevel1) {
			globalALevel1 = "";
		}
		return globalALevel1;
	}

	public void setGlobalALevel1(String globalALevel1) {
		this.globalALevel1 = globalALevel1;
	}

	public String getGlobalALevel2() {
		if(null == globalALevel2) {
			globalALevel2 = "";
		}
		return globalALevel2;
	}

	public void setGlobalALevel2(String globalALevel2) {
		this.globalALevel2 = globalALevel2;
	}

	public String getGlobalALevel3() {
		if(null == globalALevel3) {
			globalALevel3 = "";
		}
		return globalALevel3;
	}

	public void setGlobalALevel3(String globalALevel3) {
		this.globalALevel3 = globalALevel3;
	}

	public String getGlobalALevel4() {
		if(null == globalALevel4) {
			globalALevel4 = "";
		}
		return globalALevel4;
	}

	public void setGlobalALevel4(String globalALevel4) {
		this.globalALevel4 = globalALevel4;
	}

	public String getGlobalALevel5() {
		if(null == globalALevel5) {
			globalALevel5 = "";
		}
		return globalALevel5;
	}

	public void setGlobalALevel5(String globalALevel5) {
		this.globalALevel5 = globalALevel5;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getGlobalALevel() {
		globalALevel = getGlobalALevel1()+" "+getGlobalALevel2()+" "+getGlobalALevel3()+" "+getGlobalALevel4()+" "+getGlobalALevel5();
		return globalALevel;
	}

	public void setGlobalALevel(String globalALevel) {
		this.globalALevel = globalALevel;
	}

}
