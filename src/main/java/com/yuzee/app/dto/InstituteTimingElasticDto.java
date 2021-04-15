package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteTimingElasticDto {
	
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("monday")
	private String monday = "CLOSED";
	
	@JsonProperty("tuesday")
	private String tuesday = "CLOSED";
	
	@JsonProperty("wednesday")
	private String wednesday = "CLOSED";
	
	@JsonProperty("thursday")
	private String thursday = "CLOSED";
	
	@JsonProperty("friday")
	private String friday = "CLOSED";
	
	@JsonProperty("saturday")
	private String saturday = "CLOSED";
	
	@JsonProperty("sunday")
	private String sunday = "CLOSED";

}
