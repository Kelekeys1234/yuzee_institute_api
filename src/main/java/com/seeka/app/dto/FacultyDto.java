package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FacultyDto {

	@JsonProperty("faculty_id")
	private String id;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("icon")
	private String icon;

}
