package com.yuzee.app.bean;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "semester", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }, name = "UC_NA"))
public class Semester implements Serializable {

	private static final long serialVersionUID = 4519552942642063759L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

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

	public void setAuditFields(String userId, Semester existingSemester) {
		this.setUpdatedBy(userId);
		this.setUpdatedOn(new Date());
		if (existingSemester != null) {
			this.setCreatedBy(existingSemester.getCreatedBy());
			this.setCreatedOn(existingSemester.getCreatedOn());
		} else {
			this.setCreatedBy(userId);
			this.setCreatedOn(new Date());
		}
	}
}