package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteServiceDto {
	
	@Valid
	@NotEmpty(message = "services list must not be empty")
	@JsonProperty("services")
	private List<ServiceDto> services = new ArrayList<>();

}
