package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "course", indexes = { @Index(name = "IDX_FACULTY_ID", columnList = "faculty_id", unique = false),
		@Index(name = "IDX_INSTITUTE_ID", columnList = "institute_id", unique = false),
		@Index(name = "IDX_LEVEL_ID", columnList = "level_id", unique = false),
		@Index(name = "IDX_COURSE_NAME", columnList = "name", unique = false) })
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

	@Column(name = "currency", nullable = false)
	private String currency;
	
	@Column(name = "currency_time", nullable = false)
	private String currencyTime;

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
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "examination_board")
	private String examinationBoard;
	
	@Column(name = "domestic_application_fee")
	private Double domesticApplicationFee;
	
	@Column(name = "international_application_fee")
	private Double internationalApplicationFee;
	
	@Column(name = "domestic_enrollment_fee")
	private Double domesticEnrollmentFee;
	
	@Column(name = "international_enrollment_fee")
	private Double internationalEnrollmentFee;
	
	@Column(name = "entrance_exam")
	private String entranceExam;
	
	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseDeliveryModes> courseDeliveryModes = new ArrayList<>();
	
	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseEnglishEligibility> courseEnglishEligibilities = new ArrayList<>();
	
	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseIntake> courseIntakes = new ArrayList<>();
	
	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseLanguage> courseLanguages = new ArrayList<>();
	
	@OneToMany(mappedBy = "course" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseMinRequirement> courseMinRequirements = new ArrayList<>();

}