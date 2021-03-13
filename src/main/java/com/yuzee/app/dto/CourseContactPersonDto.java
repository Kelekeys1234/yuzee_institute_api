package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseContactPersonDto {

	@JsonProperty("course_contact_person_id")
	private String id;

	@JsonProperty(value = "user_id")
	@NotEmpty(message = "user_id must not be empty")
	private String userId;
	
	@JsonProperty(value = "user")
	private UserInitialInfoDto user; 
}
