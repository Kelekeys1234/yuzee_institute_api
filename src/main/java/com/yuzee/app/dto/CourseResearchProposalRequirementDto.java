package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseResearchProposalRequirementDto {

	
	@NotEmpty
	@JsonProperty("description")
	private String description;

}
