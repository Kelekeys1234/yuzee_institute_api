package com.yuzee.app.bean;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "faculty", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }, name = "UK_NA"), 
	indexes = {@Index(name = "IDX_FACULTY_NAME", columnList = "name", unique = true) })
public class Faculty implements Serializable {

	private static final long serialVersionUID = -5502957778916515394L;

	@Id
	@GenericGenerator(name = "CustomUUIDGenerator", strategy = "com.yuzee.app.util.CustomUUIDGenerator", parameters = {})
	@GeneratedValue(generator = "CustomUUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", length = 500)
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

	public Faculty(String id,String name, String description, Boolean isActive, Date createdOn, Date updatedOn, Date deletedOn,
			String createdBy, String updatedBy, Boolean isDeleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.deletedOn = deletedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isDeleted = isDeleted;
	}
}
