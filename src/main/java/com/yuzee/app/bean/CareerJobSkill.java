package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

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
/*
 * @Table(name = "job_skill", uniqueConstraints = @UniqueConstraint(columnNames
 * = { "job_id", "skill" }, name = "UK_JOB_SKILL_JOB_ID"), indexes =
 * {@Index(name = "IDX_JOB_ID", columnList = "job_id", unique = false)})
 */
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
