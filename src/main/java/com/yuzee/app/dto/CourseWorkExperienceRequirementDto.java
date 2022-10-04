package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseWorkExperienceRequirementDto {


	@NotEmpty
	@JsonProperty("description")
	private String description;

	@NotEmpty
	@JsonProperty("duration_type")
	private String durationType;

	@NotNull
	@JsonProperty("duration")
	private Double duration;

	@NotNull
	@JsonProperty("fields")
	private List<String> fields;

}
