package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CareerDto {

	@JsonProperty("career_id")
	private String id;

	@JsonProperty("career_name")
	private String name;

	@JsonProperty("job_ids")
	private List<String> jobIds;
}
