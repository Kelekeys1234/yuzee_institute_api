package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  InstituteTypeDto {

	@JsonProperty("institute_id")
	@NotBlank(message = "{institute_id}")
	private String instituteId;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("country_name")
	private String countryName;
}
