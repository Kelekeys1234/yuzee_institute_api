package com.yuzee.app.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EnrollmentCountDto {

	@JsonProperty("enrollment_count")
	@NotNull(message = "enrollment_count should not be null")
	private Integer enrollmentCount;
}
