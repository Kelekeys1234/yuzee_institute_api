package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CareerJobDto {

	@JsonProperty("job_id")
	@NotBlank(message = "job_id should not be blank")
	private String id;
	
	@JsonProperty("job_name")
	@NotBlank(message = "job_name should not be blank")
	private String jobName;
	
	@JsonProperty("job_description")
	@NotBlank(message = "job_description should not be blank")
	private String jobDescription;
}
