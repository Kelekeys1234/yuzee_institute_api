package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.institute.CourseEnglishEligibilityDto;

import lombok.Data;

@Data
public class CourseEnglishEligibilityRequestWrapper {
	@Valid
	@NotEmpty(message = "course_englist_eligibilities must not be empty")
	@JsonProperty("course_englist_eligibilities")
	ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos;

	@NotNull(message = "linked_course_ids must not be null")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
