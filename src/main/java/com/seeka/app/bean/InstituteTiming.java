package com.seeka.app.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institute_timing", indexes = {@Index(name = "IDX_INSTITUTE_ID", columnList = "institute_id", unique = false) })
public class InstituteTiming {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@Column(name = "monday")
	private String monday = "CLOSED";
	
	@Column(name = "tuesday")
	private String tuesday = "CLOSED";
	
	@Column(name = "wednesday")
	private String wednesday = "CLOSED";
	
	@Column(name = "thursday")
	private String thursday = "CLOSED";
	
	@Column(name = "friday")
	private String friday = "CLOSED";
	
	@Column(name = "saturday")
	private String saturday = "CLOSED";
	
	@Column(name = "sunday")
	private String sunday = "CLOSED";
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "institute_id")
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
