package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteTimingResponseDto {
	
	@JsonProperty("institute_timing_id")
	private String id;

	@JsonProperty("monday")
	private String monday;

	@JsonProperty("tuesday")
	private String tuesday;

	@JsonProperty("wednesday")
	private String wednesday;

	@JsonProperty("thursday")
	private String thursday;

	@JsonProperty("friday")
	private String friday;

	@JsonProperty("saturday")
	private String saturday;

	@JsonProperty("sunday")
	private String sunday;
}
