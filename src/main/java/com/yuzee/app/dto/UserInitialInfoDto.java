package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserInitialInfoDto {

	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("image_path")
	private String imagePath;
	
	@JsonProperty("designation")
	private String designation;
}
