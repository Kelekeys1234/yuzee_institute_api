package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScholarshipResponseDto extends ScholarshipDto {

	@JsonProperty("media")
	private List<StorageDto> media;

	@JsonProperty("levels")
	private List<LevelDto> levels;

	@JsonProperty("faculty")
	private FacultyDto faculty;

	@JsonProperty("institute")
	private InstituteDto institute;
}
