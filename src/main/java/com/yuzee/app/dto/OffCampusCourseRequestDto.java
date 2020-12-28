package com.yuzee.app.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class OffCampusCourseRequestDto extends OffCampusCourseDto {

	@JsonProperty("course")
	@NotNull
	@Valid
	private CourseRequest courseRequestDto;
}
