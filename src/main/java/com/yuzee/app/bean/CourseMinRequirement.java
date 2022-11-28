package com.yuzee.app.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseMinRequirement implements Serializable {

	private static final long serialVersionUID = 6903674843134844883L;
	
	@EqualsAndHashCode.Include
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

	public CourseMinRequirement(String courseMinRequirementsId, String countryName, String stateName, Double gradePoint,
			 List<CourseMinRequirementSubject> courseMinRequirementSubjects,
			Set<String> studyLanguages) {
		super();
		this.courseMinRequirementsId = courseMinRequirementsId;
		this.countryName = countryName;
		this.stateName = stateName;
		this.gradePoint = gradePoint;
		this.courseMinRequirementSubjects = courseMinRequirementSubjects;
		this.studyLanguages = studyLanguages;
	}
	
	
	
}
