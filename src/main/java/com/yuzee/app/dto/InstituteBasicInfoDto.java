package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteBasicInfoDto {

	@JsonProperty("institute_logo_path")
	private String instituteLogoPath;
	
	@NotBlank(message = "name_of_university should not be blank")
	@JsonProperty("name_of_university")
	private String nameOfUniversity;
	
	@NotBlank(message = "institute_category_type_id should not be blank")
	@JsonProperty("institute_category_type_id")
	private String instituteCategoryTypeId;
	
	@NotBlank(message = "institute_category_type_name should not be blank")
	@JsonProperty("institute_category_type_name")
	private String instituteCategoryTypeName;
	
	@NotBlank(message = "description should not be blank")
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("country_name")
	private String countryName;
	
	@JsonProperty("city_name")
	private String cityName;
	
	@JsonProperty("created_by")
	private String createdBy;
}
