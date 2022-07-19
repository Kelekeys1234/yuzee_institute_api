package com.yuzee.app.bean;

import java.io.Serializable;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yuzee.common.lib.enumeration.GradeType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Document("education_system")
@ToString
@EqualsAndHashCode
/*@Table(name = "education_system", uniqueConstraints = @UniqueConstraint(columnNames = { "country_name", "name",
		"state_name", "level_id" }, name = "UK_EDUCATION_SYSTEM_CN_NA_SN"), indexes = {
				@Index(name = "IDX_COUNTRY_NAME", columnList = "country_name", unique = false),
				@Index(name = "IDX_STATE_NAME", columnList = "state_name", unique = false) }) */
public class EducationSystem implements Serializable {

	private static final long serialVersionUID = 4954893670488436110L;

	@Id
	private UUID id;

	private String countryName;

	private String name;

	private String code;

	private String description;

	private GradeType gradeType;
	
	private String stateName;

	@DBRef
	private Level level;
	
	private Boolean isActive;

	private Date createdOn;

	private Date updatedOn;

	private Date deletedOn;

	private String createdBy;

	private String updatedBy;

	private Boolean isDeleted;

}
