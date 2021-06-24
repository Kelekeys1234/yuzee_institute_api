package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.institute.CareerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerJobDto {

	@JsonProperty("job_id")
	@NotBlank(message = "job_id should not be blank")
	private String id;
	
	@JsonProperty("job_name")
	@NotBlank(message = "job_name should not be blank")
	private String job;
	
	@JsonProperty("job_description")
	@NotBlank(message = "job_description should not be blank")
	private String jobDescription;
	
	@JsonProperty("career")
	CareerDto careers;
	
	@JsonProperty("working_styles")
	List<CareerJobWorkingStyleDto> careerJobWorkingStyles;

	@JsonProperty("working_activities")
	List<CareerJobWorkingActivityDto> careerJobWorkingActivities;
	
	@JsonProperty("job_skills")
	List<CareerJobSkillDto> careerJobSkills;

	@JsonProperty("last_selected")
	private boolean lastSelected;
}
