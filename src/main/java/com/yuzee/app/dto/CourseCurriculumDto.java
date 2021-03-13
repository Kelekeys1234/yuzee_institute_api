package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseCurriculumDto {

	@JsonProperty("course_curriculum_id")
	private String id;

	@JsonProperty("name")
	private String name;
}
