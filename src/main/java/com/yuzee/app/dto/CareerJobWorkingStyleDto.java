package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CareerJobWorkingStyleDto {

	@JsonProperty("working_activity_id")
	@NotBlank(message = "working_activity_id should not be blank")
	private String id;
	
	@JsonProperty("working_activity")
	@NotBlank(message = "working_activity should not be blank")
	private String workActivity;
	
	@JsonProperty("job_id")
	@NotBlank(message = "job_id should not be blank")
	private String jobId;
}
