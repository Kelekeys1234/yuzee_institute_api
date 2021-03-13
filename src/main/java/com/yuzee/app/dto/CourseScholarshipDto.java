package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CourseScholarshipDto {
	@JsonProperty("course_scholarship_id")
	private String id;

	@JsonProperty("description")
	@NotEmpty(message = "description should not be blank")
	private String description;

	@JsonProperty(value = "scholarship_ids",access = Access.WRITE_ONLY)
	@NotEmpty(message = "scholarship_ids must not be empty")
	private List<String> scholarshipIds = new ArrayList<>();

	@JsonProperty("scholarships")
	private List<ScholarshipResponseDto> scholarships = new ArrayList<>();

	@NotNull(message = "linked_course_ids must not be null")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
