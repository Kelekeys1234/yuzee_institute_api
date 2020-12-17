package com.yuzee.app.dto;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class OffCampusCourseRequestDto extends OffCampusCourseDto {

	@JsonProperty("course")
	@Valid
	private CourseRequest courseRequestDto;
}
