package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseFundingRequestWrapper {
	@Valid
	@NotEmpty(message = "course_fundings must not be empty")
	@JsonProperty("course_fundings")
	ValidList<CourseFundingDto> courseFundingDtos;

	@NotNull(message = "linked_course_ids must not be null")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
