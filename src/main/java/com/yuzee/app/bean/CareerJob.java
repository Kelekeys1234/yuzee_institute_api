package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Document("jobs")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
/*
 * @Table(name = "jobs", uniqueConstraints = @UniqueConstraint(columnNames = {
 * "job", "career_id" }, name = "UK_JOB_CAREER_ID"), indexes = {@Index(name =
 * "IDX_CAREER_ID", columnList = "career_id", unique = false)})
 */
public class CareerJob implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String job;

	private String jobDescription;

	private String responsibility;

	private Integer courseLevel;

	private Date createdOn;

	private Date updatedOn;

	private String createdBy;

	private String updatedBy;

	// Delete CareerJobCourseSearchKeyword model
	private List<String> careerJobCourseSearchKeywords = new ArrayList<>();

	// Delete CareerJobLevel model
	@DBRef
	private List<Level> careerJobLevels = new ArrayList<>();

	private List<CareerJobSkill> careerJobSkills = new ArrayList<>();

	// Delete CareerJobSubject model
	private List<String> careerJobSubjects = new ArrayList<>();

	// Delete CareerJobType model
	private List<String> careerJobTypes = new ArrayList<>();

	// Delete CareerJobWorkingActivity model
	private List<String> careerJobWorkingActivities = new ArrayList<>();

	// Delete CareerJobWorkingStyle model
	private List<String> careerJobWorkingStyles = new ArrayList<>();

	public CareerJob(String jobs, String jobDescription, String responsibility, Integer courseLevel, Date createdOn,
			String createdBy) {
		this.job = jobs;
		this.jobDescription = jobDescription;
		this.responsibility = responsibility;
		this.courseLevel = courseLevel;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}
}
