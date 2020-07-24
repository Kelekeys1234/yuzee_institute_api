package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoursePrerequisiteSubjectDto {

	@JsonProperty("course_prerequisite_id")
	@NotBlank(message = "course_prerequisite_id should not be blank")
	private String coursePrerequisiteId;

	@JsonProperty("subject_name")
	@NotBlank(message = "subject_name should not be blank")
	private String subjectName;
	
	@JsonProperty("subject_grade")
	@NotNull(message = "subject_grade should not be null")
	private Double subjectGrade;
	
	@JsonProperty("subject_type")
	@NotNull(message = "subject_type should not be null")
	private String subjectType;
	
	public CoursePrerequisiteSubjectDto(String coursePrerequisiteId, String subjectName, Double subjectGrade, String subjectType) {
		this.coursePrerequisiteId = coursePrerequisiteId;
		this.subjectName = subjectName;
		this.subjectGrade = subjectGrade;
		this.subjectType = subjectType;
	}
}
