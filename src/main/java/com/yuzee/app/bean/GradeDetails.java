package com.yuzee.app.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
//@Table(name = "grade_details", indexes = {
//		@Index(name = "IDX_COUNTRY_NAME", columnList = "country_name", unique = false),
//		@Index(name = "IDX_EDUCATION_SYSTEM_ID", columnList = "education_system_id", unique = false) })
@Document(collection="grade")
public class GradeDetails implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

//	@Column(name = "country_name", nullable = false)
	private String countryName;

	@DBRef(lazy = true)
	private EducationSystem educationSystem;

//	@Column(name = "grade", nullable = false)
	private String grade;

//	@Column(name = "gpa_grade")
	private String gpaGrade;

//	@Column(name = "state_name", nullable = false)
	private String stateName;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_on", length = 19)
	private Date createdOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

//	@Column(name = "created_by", length = 50)
	private String createdBy;

//	@Column(name = "updated_by", length = 50)
	private String updatedBy;
}
