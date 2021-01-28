package com.yuzee.app.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseMinRequirementDto {

	@JsonProperty("course_min_requirement_id")
	private String id;

	@JsonProperty("country_name")
	@NotEmpty(message = "country_name must not be empty")
	private String countryName;

	@JsonProperty("state_name")
	@NotEmpty(message = "state_name must not be empty")
	private String stateName;

	@JsonProperty("education_system_id")
	@NotEmpty(message = "educationSystemId must not be empty")
	private String educationSystemId;
	
	@JsonProperty("education_system")
	private EducationSystemDto educationSystem;

	@JsonProperty("grade_point")
	@NotNull(message = "grade_point must not be null")
	private Double gradePoint;

	@Valid
	@JsonProperty("min_requirement_subjects")
	@NotNull(message = "min_requirement_subjects must not be null")
	private ValidList<CourseMinRequirementSubjectDto> minRequirementSubjects;
}
