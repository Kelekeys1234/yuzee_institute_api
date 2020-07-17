package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ScholarshipCountDto {

	@JsonProperty("level_name")
	@NotBlank(message = "level_name should not be blank")
	private String levelName;
	
	@JsonProperty("level_name")
	@NotNull(message = "level_name should not be blank")
	private Long count;
	
	public ScholarshipCountDto(String levelName, Long count) {
		this.levelName = levelName;
		this.count = count;
	}
}
