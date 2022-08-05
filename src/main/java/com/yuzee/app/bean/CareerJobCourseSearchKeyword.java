package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "careerJobs")
@EqualsAndHashCode
//@Table(name = "job_course_search_keyword", uniqueConstraints = @UniqueConstraint(columnNames = { "job_id",
//		"course_search_keyword" }, name = "UK_CSK_JOB_ID"), indexes = {
//				@Index(name = "IDX_JOB_ID", columnList = "job_id", unique = false) })
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

	@Column(name = "created_by", length = 50)
	private String createdBy;

	private String updatedBy;
}
