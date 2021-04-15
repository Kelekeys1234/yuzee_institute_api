package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteEnglishRequirementsElasticDto {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("exam_name")
	private String examName;
	
	@JsonProperty("reading")
	private Double reading;
	
	@JsonProperty("writing")
	private Double writing;
	
	@JsonProperty("oral")
	private Double oral;
	
	@JsonProperty("listening")
	private Double listening;
	
}
