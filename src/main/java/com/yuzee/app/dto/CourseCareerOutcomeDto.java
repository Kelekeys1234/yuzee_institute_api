package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CourseCareerOutcomeDto {

	@JsonProperty("course_career_outcome_id")
	private String id;

	@JsonProperty(value = "career_id", access = Access.WRITE_ONLY)
	@NotEmpty(message = "career_id must not be null")
	private String careerId;

	@JsonProperty(value = "career", access = Access.READ_ONLY)
	private CareerDto career;
}
