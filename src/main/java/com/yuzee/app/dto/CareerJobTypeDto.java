package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CareerJobTypeDto {

	@JsonProperty("job_type_id")
	private String id;
	
	@JsonProperty("job_type")
	private String type;
}
