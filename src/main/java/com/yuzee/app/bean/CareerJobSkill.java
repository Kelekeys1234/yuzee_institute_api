package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection="careerJobSkill")

public class CareerJobSkill implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String skill;
	private String levelId;
	private String jobId;
	private String description;
	@DBRef(lazy = true)
	private CareerJob careerJobs;
	private Date createdOn;
	private String createdBy;

}
