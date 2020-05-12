package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseMobileDto {

	@NotBlank(message = "course_name should not be blank")
	@JsonProperty("course_name")
	private String courseName;
	
	@NotBlank(message = "course_description should not be blank")
	@JsonProperty("course_description")
	private String courseDescription;
	
	@NotBlank(message = "faculty_id should not be blank")
	@JsonProperty("faculty_id")
	private String facultyId;
	
	@JsonProperty("faculty_name")
	private String facultyName;
	
	@NotNull(message = "domestic_fee should not be blank")
	@JsonProperty("domestic_fee")
	private Double usdDomesticFee;
	
	@NotNull(message = "international_fee should not be blank")
	@JsonProperty("international_fee")
	private Double usdInternationalFee;
	
	@NotNull(message = "duration should not be blank")
	@JsonProperty("duration")
	private Double duration;
	
	@NotBlank(message = "duration_unit should not be blank")
	@JsonProperty("duration_unit")
	private String durationUnit;
}
