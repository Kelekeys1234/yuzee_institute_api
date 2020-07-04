package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "education_agent_partnerships")
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
