package com.seeka.freshfuture.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="faculty")
public class Faculty implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="course_type_id")
	private CourseType courseTypeObj; //Course Type ID
	
	@Column(name="name")
	private String name; //Faculty Name
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CourseType getCourseTypeObj() {
		return courseTypeObj;
	}

	public void setCourseTypeObj(CourseType courseTypeObj) {
		this.courseTypeObj = courseTypeObj;
	}
 
	
}
