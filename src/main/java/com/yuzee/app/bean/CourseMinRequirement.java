package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.yuzee.common.lib.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementSubjectDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data

@NoArgsConstructor
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
	
	private String courseMinRequirementsId;
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

	public CourseMinRequirement(String courseMinRequirementsId, String countryName, String stateName, Double gradePoint, Set<String> studyLanguages) {
		super();
		this.courseMinRequirementsId = courseMinRequirementsId;
		this.countryName = countryName;
		this.stateName = stateName;
		this.gradePoint = gradePoint;
		this.studyLanguages = studyLanguages;
	}
	
	
}
