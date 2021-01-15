package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScholarshipRequestDto extends ScholarshipDto {
	@JsonProperty("level_id")
	@NotBlank(message = "level_id should not be blank")
	private String levelId;

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;

	@JsonProperty("faculty_id")
	@NotBlank(message = "institute_id should not be blank")
	private String facultyId;

}
