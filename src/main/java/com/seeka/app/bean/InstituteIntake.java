package com.seeka.app.bean;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "institute_intake", uniqueConstraints = @UniqueConstraint(columnNames = { "intake", "institute_id" } , name = "UK_INSTITUTE_ID_INTAKE"),
       indexes = {@Index(name = "IDX_INSTITUTE_ID", columnList = "institute_id", unique = false) })
public class InstituteIntake {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;

	@Column(name = "intake")
	private String intake;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id", nullable = false)
	private Institute institute;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on")
	private Date deletedOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;
}