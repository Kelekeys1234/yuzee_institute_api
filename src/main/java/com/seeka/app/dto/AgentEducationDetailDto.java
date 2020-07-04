package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AgentEducationDetailDto {

	@JsonProperty("course_id")
	@NotBlank(message = "course_id should not be blank")
    private String courseId;
	
	@JsonProperty("duration_from")
	@NotBlank(message = "duration_from should not be blank")
    private String durationFrom;
	
	@JsonProperty("duration_to")
	@NotBlank(message = "duration_to should not be blank")
    private String durationTo;
	
	@JsonProperty("institute_d")
	@NotBlank(message = "institute_d should not be blank")
    private String instituteId;
}
