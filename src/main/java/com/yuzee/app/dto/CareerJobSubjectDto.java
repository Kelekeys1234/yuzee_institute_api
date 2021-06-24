package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CareerJobSubjectDto {

	@JsonProperty("job_subject_id")
	@NotBlank(message = "job_subject_id should not be blank")
	private String id;
	
	@JsonProperty("subject")
	@NotBlank(message = "subject should not be blank")
	private String subject;
	
	@JsonProperty("job_id")
	@NotBlank(message = "job_id should not be blank")
	private String jobId;
	
	@JsonProperty("last_selected")
	private boolean lastSelected;
}
