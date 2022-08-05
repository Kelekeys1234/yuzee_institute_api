package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

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

@Document(collection = "careerJobWorkingActivity")
public class CareerJobWorkingActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

//	@Column(name = "work_activities", nullable = false, columnDefinition = "text")
	private String workActivities;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "job_id")
	@DBRef(lazy = true)
	private CareerJob careerJobs;
//	
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_on", length = 19)
	private Date createdOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

//	@Column(name = "created_by", length = 50)
	private String createdBy;

//	@Column(name = "updated_by", length = 50)
	private String updatedBy;
}
