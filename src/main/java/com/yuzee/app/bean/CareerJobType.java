package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = "careerJobs")
@EqualsAndHashCode
@Document(collection = "job_type")
public class CareerJobType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String jobType;

	private CareerJob careerJobs;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
