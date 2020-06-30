package com.seeka.app.dto;

import lombok.Data;

@Data
public class CourseEnglishEligibilityDto {
	private String id;
	private String englishType;
	private Double reading;
	private Double writing;
	private Double speaking;
	private Double listening;
	private Double overall;
}
