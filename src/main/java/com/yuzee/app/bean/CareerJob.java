package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "jobs", uniqueConstraints = @UniqueConstraint(columnNames = { "jobs", "career_id" }, 
	 name = "UK_JOB_CAREER_ID"), indexes = {@Index(name = "IDX_CAREER_ID", columnList = "career_id", unique = false)})
public class CareerJob implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
	private String id;
	
	@Column(name = "jobs", nullable = false)
	private String jobs;
	
	@Column(name = "job_description", nullable = false)
	private String jobDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "career_id")
	private Careers careerList;
	
	@Column(name = "course_level", nullable = false)
	private Integer courseLevel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	
	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobCourseSearchKeyword> careerJobCourseSearchKeywords = new ArrayList<>();
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobLevel> careerJobLevels = new ArrayList<>();
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobSkill> careerJobSkills = new ArrayList<>();
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobSubject> careerJobSubjects = new ArrayList<>();
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobType> careerJobTypes = new ArrayList<>();
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobWorkingActivity> careerJobWorkingActivities = new ArrayList<>();
	
	@OneToMany(mappedBy = "careerJobs" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CareerJobWorkingStyle> careerJobWorkingStyles = new ArrayList<>();
	
	public CareerJob(String jobs, String jobDescription, Careers careerList, Integer courseLevel, Date createdOn, String createdBy) {
		this.jobs = jobs;
		this.jobDescription = jobDescription;
		this.careerList = careerList;
		this.courseLevel = courseLevel;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
	}
}
