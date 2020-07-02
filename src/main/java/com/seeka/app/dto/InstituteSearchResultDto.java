package com.seeka.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteSearchResultDto {

	@JsonProperty("institute_id")
	private String instituteId;

	@JsonProperty("institute_name")
	private String instituteName;

	@JsonProperty("location")
	private String location;
}
