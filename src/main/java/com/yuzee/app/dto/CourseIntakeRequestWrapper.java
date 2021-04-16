package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.institute.CourseIntakeDto;

import lombok.Data;

@Data
public class CourseIntakeRequestWrapper {
	@Valid
	@NotEmpty(message = "course_intakes must not be empty")
	@JsonProperty("course_intakes")
	ValidList<CourseIntakeDto> courseIntakeDtos;

	@NotNull(message = "linked_course_ids must not be null")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
