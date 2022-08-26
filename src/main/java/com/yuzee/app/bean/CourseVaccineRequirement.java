package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*
 * @Table(name = "course_vaccine_requirement", indexes = {
 * 
 * @Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
 */
public class CourseVaccineRequirement implements Serializable {

	private static final long serialVersionUID = 8492390790670110780L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String description;
	@DBRef
	private Course course;
	private String auditFields;
	@EqualsAndHashCode.Include
	private Set<String> vaccinationIds;

}
