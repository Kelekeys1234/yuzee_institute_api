package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteServiceDto {
	
	@JsonProperty("services")
	private List<ServiceDto> services = new ArrayList<ServiceDto>();

}
