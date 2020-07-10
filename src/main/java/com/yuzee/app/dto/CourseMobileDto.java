package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseMobileDto {
	
	@JsonProperty("course_id")
	private String courseId;

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
	
	@NotNull(message = "gpa_required should not be blank")
	@JsonProperty("gpa_required")
	private Double gpaRequired;
	
	@NotNull(message = "usd_domestic_fee should not be blank")
	@JsonProperty("usd_domestic_fee")
	private Double usdDomesticFee;
	
	@NotNull(message = "usd_international_fee should not be blank")
	@JsonProperty("usd_international_fee")
	private Double usdInternationalFee;
	
	@NotNull(message = "duration should not be blank")
	@JsonProperty("duration")
	private Double duration;
	
	@NotBlank(message = "duration_unit should not be blank")
	@JsonProperty("duration_unit")
	private String durationUnit;
}
