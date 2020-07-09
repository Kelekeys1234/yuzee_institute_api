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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "scholarship", uniqueConstraints = @UniqueConstraint(columnNames = { "country_name", "level_id", "institute_name", "course_name" },
	   name = "UK_CN_LE_IN_CN"), indexes = {@Index(name = "IDX_LEVEL_ID", columnList = "level_id", unique = false),
	   @Index(name = "IDX_COUNTRY_NAME", columnList = "country_name", unique = false) })
public class Scholarship implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "scholarship_award")
	private String scholarshipAward;
	
	@Column(name = "country_name", nullable = false)
	private String countryName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id", nullable = false)
	private Level level;
	
	@Column(name = "number_of_avaliability", nullable = false)
	private Integer numberOfAvaliability;
	
	@Column(name = "currency", nullable = false)
	private String currency;
	
	@Column(name = "scholarship_amount", nullable = false)
	private Double scholarshipAmount;
	
	@Column(name = "validity", nullable = false)
	private String validity;
	
	@Column(name = "how_to_apply", nullable = false)
	private String howToApply;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	@Column(name = "eligible_nationality", nullable = false)
	private String eligibleNationality;
	
	@Column(name = "headquaters", nullable = false)
	private String headquaters;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "address", nullable = false)
	private String address;
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;
	
	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "website", nullable = false)
	private String website;
	
	@Column(name = "institute_name", nullable = false)
	private String instituteName;
	
	@Column(name = "course_name", nullable = false)
	private String courseName;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "application_deadline", length = 19)
	private Date applicationDeadline;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "requirements", nullable = false)
	private String requirements;

}
