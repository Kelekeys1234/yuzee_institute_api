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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "education_agent_skill")
public class EducationAgentSkill implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "skill", nullable = false)
	private String skill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "education_agent_id", nullable = false)
	private EducationAgent educationAgent;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Column(name = "deleted_by", length = 50)
	private String deletedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;

}
