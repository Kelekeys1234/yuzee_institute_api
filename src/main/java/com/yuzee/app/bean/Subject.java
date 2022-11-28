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
@Document(collection = "subject")
public class Subject implements java.io.Serializable {

	private static final long serialVersionUID = -4896547771928499529L;

	@org.springframework.data.annotation.Id
	private String id;

	private String code;

	private String name;

	@DBRef(lazy = true)
	private EducationSystem educationSystem;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;
}