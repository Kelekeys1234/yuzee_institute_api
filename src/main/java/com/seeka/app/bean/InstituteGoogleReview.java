package com.seeka.app.bean;

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
@Table(name = "institute_google_review")
public class InstituteGoogleReview {

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institute_id", nullable = false)
	private Institute institute;
	
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(name = "date_of_posting", nullable = true)
	private Date dateOfPosting;
	
	@Column(name = "review_star", nullable = false)
	private Double reviewStar;
	
	@Column(name = "review_comment", nullable = true)
	private String reviewComment;
	
	@Column(name = "country_name")
	private String countryName;
	
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
}
