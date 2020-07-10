package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

	@NotBlank(message = "service_id is required") 
	@JsonProperty("service_id")
	private String serviceId;
	
	@JsonProperty("service_name")
	private String serviceName;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("icon")
	private String icon;
}
