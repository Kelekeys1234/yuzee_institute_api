package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseVaccineRequirementDto {

	@JsonProperty("id")
	private String id;

	@NotEmpty
	@JsonProperty("description")
	private String description;

	@NotNull
	@JsonProperty("details")
	private List<String> details;

}
