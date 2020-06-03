package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class LevelDto {
	private String id;
	private String name;
	private String code;
	private List<FacultyDto> faculty;
}
