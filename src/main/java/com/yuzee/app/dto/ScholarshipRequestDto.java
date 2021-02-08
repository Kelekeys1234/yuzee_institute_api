package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScholarshipRequestDto extends ScholarshipDto {
	@JsonProperty("level_ids")
	@NotNull(message = "level_ids should not be null")
	private List<String> levelIds;

	@JsonProperty("institute_id")
	@NotBlank(message = "institute_id should not be blank")
	private String instituteId;

	@JsonProperty("faculty_id")
	@NotBlank(message = "faculty_id should not be blank")
	private String facultyId;

}
