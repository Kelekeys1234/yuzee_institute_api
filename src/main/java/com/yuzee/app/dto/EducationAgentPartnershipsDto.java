package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EducationAgentPartnershipsDto {

	@JsonProperty("agent_id")
	@NotBlank(message = "agent_id should not be blank")
	private String agentId;

	@JsonProperty("course_id")
	@NotBlank(message = "course_id should not be blank")
	private String courseId;

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;

	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;

}
