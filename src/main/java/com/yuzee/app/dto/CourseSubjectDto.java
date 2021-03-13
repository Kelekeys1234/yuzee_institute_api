package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSubjectDto {

	@JsonProperty("course_subject_id")
	private String id;

	@JsonProperty(value = "semester")
	private String semester;

	@NotEmpty(message = "name must not be empty")
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
}
