package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseMinRequirementSubjectDto {
	@JsonProperty("course_min_requirement_subject_id")
	private String id;

	@JsonProperty("name")
	@NotEmpty(message = "name must not be empty")
	private String name;

	@JsonProperty("grade")
	@NotEmpty(message = "grade must not be empty")
	private String grade;
}