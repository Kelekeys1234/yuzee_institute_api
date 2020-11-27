package com.yuzee.app.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Valid
public class InstituteServiceDto {

	@JsonProperty("institute_service_id")
	private String id;

	@NotNull(message = "service must not be null")
	@JsonProperty("service")
	private ServiceDto service;

	@JsonProperty("description")
	private String description;
}
