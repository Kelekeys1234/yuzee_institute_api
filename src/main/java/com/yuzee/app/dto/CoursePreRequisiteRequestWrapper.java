package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.institute.CoursePreRequisiteDto;

import lombok.Data;

@Data
public class CoursePreRequisiteRequestWrapper {
	@Valid
	@NotEmpty(message = "course_pre_requisites must not be empty")
	@JsonProperty("course_pre_requisites")
	ValidList<CoursePreRequisiteDto> coursePreRequisiteDtos;

	@NotNull(message = "linked_course_ids must not be null")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
