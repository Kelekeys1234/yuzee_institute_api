package com.seeka.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseMinRequirementDto {
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("system")
	private String system;
	
	@JsonProperty("subject")
	private List<String> subject;
	
	@JsonProperty("grade")
	private List<String> grade;
	
	@JsonProperty("course")
	private String course;
}
