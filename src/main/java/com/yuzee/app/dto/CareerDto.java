package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerDto {

	@JsonProperty("career_id")
	private String id;

	@JsonProperty("career_name")
	private String career;

	@JsonProperty("job_ids")
	private List<String> jobIds;
}
