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
@Document("job_course_search_keyword")
public class CareerJobCourseSearchKeyword implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	private String id;

	private String courseSearchKeyword;

	@DBRef(lazy = false)
	private CareerJob careerJobs;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;
}
