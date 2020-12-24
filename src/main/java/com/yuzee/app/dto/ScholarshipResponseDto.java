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

	@JsonProperty("level")
	private LevelDto level;

	@JsonProperty("institute")
	private InstituteDto institute;
}
