package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "careerJobs")
@EqualsAndHashCode
//@Table(name = "job_level", uniqueConstraints = @UniqueConstraint(columnNames = { "job_id", "level_id" }, 
//name = "UK_JOB_LEVEL_JOB_ID"), indexes = {@Index(name = "IDX_JOB_ID", columnList = "job_id", unique = false)})
public class CareerJobLevel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

	@DBRef(lazy = true)
	private CareerJob careerJobs;

	@DBRef(lazy = true)
	private Level level;

	private Date createdOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

//	@Column(name = "created_by", length = 50)
	private String createdBy;

//	@Column(name = "updated_by", length = 50)
	private String updatedBy;
}
