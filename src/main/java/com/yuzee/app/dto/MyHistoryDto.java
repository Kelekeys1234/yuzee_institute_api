package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.common.lib.dto.storage.StorageDto;

import lombok.Data;

@Data
public class MyHistoryDto {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("course_name")
	@NotBlank(message = "course_name should not be blank")
	private String name;
	
	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;
	
	@JsonProperty("institute_name")
	@NotBlank(message = "institute_name should not be blank")
	private String instituteName;
	
	@JsonProperty("city_name")
	@NotBlank(message = "city_name should not be blank")
	private String cityName;
	
	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;
	
	@JsonProperty("stars")
	private Double stars;
	
	@JsonProperty("storage")
	private List<StorageDto> storage;
}
