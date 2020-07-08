package com.seeka.app.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
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
@Table(name = "grade_details", indexes = {@Index(name = "IDX_COUNTRY_NAME", columnList = "country_name", unique = false),
		   @Index(name = "IDX_EDUCATION_SYSTEM_ID", columnList = "education_system_id", unique = false) })
public class GradeDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@Column(name = "country_name")
	private String countryName;
	
	@Column(name = "education_system_id")
	private String educationSystemId;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "gpa_grade")
	private String gpaGrade;
	
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
