package com.yuzee.app.bean;

import java.io.Serializable;

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
@Table(name = "education_agent_accomplishment", uniqueConstraints = @UniqueConstraint(columnNames = { "education_agent_id", "accomplishment" }, name = "UK_EA_AC"),
       indexes = {@Index(name = "IDX_EDUCATION_SYSTEM_ID", columnList = "education_agent_id", unique = false)})
public class EducationAgentAccomplishment implements Serializable {

	private static final long serialVersionUID = 5111616897386950348L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "accomplishment", nullable = false)
	private String accomplishment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "education_agent_id", nullable = false)
	private EducationAgent educationAgent;
}
