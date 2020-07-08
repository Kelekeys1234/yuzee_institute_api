package com.seeka.app.bean;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "education_system", uniqueConstraints = @UniqueConstraint(columnNames = { "country_name", "name", "state_name" }, name = "UK_CN_NA_SN"),
	indexes = {@Index(name = "IDX_COUNTRY_NAME", columnList = "country_name", unique = false),
			   @Index(name = "IDX_STATE_NAME", columnList = "state_name", unique = false) })
public class EducationSystem implements Serializable {

	private static final long serialVersionUID = 4954893670488436110L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@Column(name = "country_name", nullable = false)
	private String countryName;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;
	
	@Column(name = "created_by", length = 50)
	private String createdBy;
	
	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "state_name", nullable = false)
	private String stateName;
}
