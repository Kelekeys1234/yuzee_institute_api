package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteIdsRequestWrapperDto {

	@NotEmpty(message = "institute_ids must not be empty.")
	@JsonProperty("institute_ids")
	List<String> instituteIds;
}
