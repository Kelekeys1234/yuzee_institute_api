package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSubjectDto {

	@JsonProperty("course_subject_id")
	private String id;

	@JsonProperty(value = "semester_id", access = Access.WRITE_ONLY)
	private String semesterId;

	@JsonProperty(value = "semester", access = Access.READ_ONLY)
	private SemesterDto semester;

	@NotEmpty(message = "name must not be empty")
	@JsonProperty("name")
	private String name;
}
