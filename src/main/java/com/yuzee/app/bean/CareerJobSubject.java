package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = "careerJobs")
@EqualsAndHashCode
@Document(collection = "CareerJobSubject")
public class CareerJobSubject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String subject;

	@DBRef(lazy = true)
	private CareerJob careerJobs;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
