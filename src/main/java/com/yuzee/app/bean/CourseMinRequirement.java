package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Table(name = "course_min_requirement", uniqueConstraints = @UniqueConstraint(columnNames = { "country_name",
		"state_name", "education_system_id", "course_id" }, name = "UK_COURSE_CN_SN_ESI_C"), indexes = {
				@Index(name = "IDX_COURSE_ID", columnList = "course_id", unique = false) }) */
public class CourseMinRequirement implements Serializable {

	private static final long serialVersionUID = 6903674843134844883L;

	@EqualsAndHashCode.Include
	private String countryName;

	@EqualsAndHashCode.Include
	private String stateName;

	@EqualsAndHashCode.Include
	private Double gradePoint;

	@DBRef
	private EducationSystem educationSystem;

	@EqualsAndHashCode.Include
	private List<CourseMinRequirementSubject> courseMinRequirementSubjects = new ArrayList<>();

	private Set<String> studyLanguages = new HashSet<>();	
}
