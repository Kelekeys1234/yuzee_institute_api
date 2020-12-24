package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterDto {

	@JsonProperty("semester_id")
	private String id;
	
	@NotEmpty(message = "name must not be empty")
	@JsonProperty("name")
	private String name;
}
