package com.yuzee.app.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Document("institute_keywords")
public class InstituteKeywords {

	@org.springframework.data.annotation.Id
	private String id;

	private String keyword;
	
	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;
}
