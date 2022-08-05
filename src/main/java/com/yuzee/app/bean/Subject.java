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
@Document(collection = "subject")
public class Subject implements java.io.Serializable {

	private static final long serialVersionUID = -4896547771928499529L;

	@Id
//	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;

//	@Column(name = "code", nullable = false)
	private String code;

//	@Column(name = "name", nullable = false)
	private String name;

	@DBRef(lazy = true)
	private EducationSystem educationSystem;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_on", length = 19)
	private Date createdOn;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "deleted_on", length = 19)
	private Date deletedOn;

//	@Column(name = "created_by", length = 50)
	private String createdBy;

//	@Column(name = "updated_by", length = 50)
	private String updatedBy;
}