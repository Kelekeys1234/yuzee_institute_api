package com.seeka.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class  InstituteTypeDto {
	
	@JsonProperty("institute_type_id")
	private String instituteTypeId;
	
	@JsonProperty("institute_type")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("country_name")
	private String countryName;
}
