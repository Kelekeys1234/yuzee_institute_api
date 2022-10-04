package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.yuzee.common.lib.dto.ValidList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModesDto;

import lombok.Data;

@Data
public class CourseDeliveryModeRequestWrapper {
	@Valid
	@NotEmpty(message = "{course_delivery_modes.is_required}")
	@JsonProperty("course_delivery_modes")
	ValidList<CourseDeliveryModesDto> courseDelieveryModeDtos;

	@NotNull(message = "{linked_course_ids.is_required}")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
