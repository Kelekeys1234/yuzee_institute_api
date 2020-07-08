package com.seeka.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteTimingDto {
	
	@JsonProperty("opening_from")
	private String openingFrom;
	
	@JsonProperty("opening_to")
	private String openingTo;
	
	@JsonProperty("day")
	private String day;
}
