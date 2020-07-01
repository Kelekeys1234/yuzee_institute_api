package com.seeka.app.bean;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

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
@Table(name = "course")
public class Course implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	private Faculty faculty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id")
	private Institute institute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id")
	private Level level;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "world_ranking")
	private Integer worldRanking;

	@Column(name = "stars")
	private Integer stars;

	@Column(name = "recognition")
	private String recognition;

	@Column(name = "recognition_type")
	private String recognitionType;

	@Column(name = "website")
	private String website;

	@Column(name = "abbreviation")
	private String abbreviation;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "description")
	private String description;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "availabilty", nullable = false)
	private String availabilty;

	@Column(name = "intake")
	private String intake;

	@Column(name = "contact")
	private String contact;

	@Column(name = "opening_hour_from")
	private String openingHourFrom;

	@Column(name = "opening_hour_to")
	private String openingHourTo;

	@Column(name = "campus_location")
	private String campusLocation;

	@Column(name = "job_full_time")
	private String jobFullTime;

	@Column(name = "job_part_time")
	private String jobPartTime;

	@Column(name = "link")
	private String link;

	@Column(name = "currency", nullable = false)
	private String currency;
	
	@Column(name = "currency_time", nullable = false)
	private String currencyTime;

	@Column(name = "cost_range", precision = 18, scale = 3)
	private Double costRange;

	@Column(name = "global_gpa")
	private Double globalGpa;
	
	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rec_date", length = 19)
	private Date recDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;

}