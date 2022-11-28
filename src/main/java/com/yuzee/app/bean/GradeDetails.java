package com.yuzee.app.bean;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode

@Document(collection = "grade")
public class GradeDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@org.springframework.data.annotation.Id
	private String id;

	private String countryName;

	@DBRef(lazy = true)
	private EducationSystem educationSystem;

	private String grade;

	private String gpaGrade;

	private String stateName;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
