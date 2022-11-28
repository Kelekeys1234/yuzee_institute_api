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
@Document(collection = "careerJobs")
public class CareerJobLevel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@DBRef(lazy = true)
	private CareerJob careerJobs;

	@DBRef(lazy = true)
	private Level level;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
