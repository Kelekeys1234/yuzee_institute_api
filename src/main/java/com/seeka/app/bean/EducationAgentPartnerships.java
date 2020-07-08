package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "education_agent_partnerships", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "institute_id", "education_agent_id" }, name = "UK_EA_CI_IN"),
indexes = {@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false),
		   @Index(name = "IDX_INSTITUTE_ID", columnList = "institute_id", unique = false),
		   @Index(name = "IDX_EDUCATION_AGENT_ID", columnList = "education_agent_id", unique = false)})
public class EducationAgentPartnerships implements Serializable {

	private static final long serialVersionUID = -3766742694768712352L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id", nullable = false)
	private Institute institute;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "deleted_on")
	private Date DeletedOn;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "education_agent_id", nullable = false)
	private EducationAgent educationAgent;

	@Column(name = "country_name")
	private String countryName;

}
