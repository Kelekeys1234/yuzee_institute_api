package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * @Table(name = "course_min_requirement", uniqueConstraints
 * = @UniqueConstraint(columnNames = { "country_name", "state_name",
 * "education_system_id", "course_id" }, name = "UK_COURSE_CN_SN_ESI_C"),
 * indexes = {
 * 
 * @Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) })
 */
public class CourseMinRequirement implements Serializable {

	private static final long serialVersionUID = 6903674843134844883L;
	@Id
	private String id;
	@EqualsAndHashCode.Include
	private String countryName;

	@EqualsAndHashCode.Include
	private String stateName;

	@EqualsAndHashCode.Include
	private Double gradePoint;

	@DBRef
	private EducationSystem educationSystem;
	@DBRef
	private Course course;
	private String auditFields;

	@EqualsAndHashCode.Include
	private List<CourseMinRequirementSubject> courseMinRequirementSubjects = new ArrayList<>();

	private Set<String> studyLanguages = new HashSet<>();
}
