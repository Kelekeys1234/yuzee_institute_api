package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteEnglishRequirementsDto {

	@JsonProperty("institute_english_requirements_id")
	public String instituteEnglishRequirementsId;
		
	@NotBlank(message = "exam_name is required")
	@JsonProperty("exam_name")
	private String examName;
	
	@NotNull(message = "reading_marks is required")
	@JsonProperty("reading_marks")
	private Double readingMarks;
	
	@NotNull(message = "listning_marks is required")
	@JsonProperty("listning_marks")
	private Double listningMarks;
	
	@NotNull(message = "writing_marks is required")
	@JsonProperty("writing_marks")
	private Double writingMarks;
	
	@NotNull(message = "oral_marks is required")
	@JsonProperty("oral_marks")
	private Double oralMarks;
	
	@JsonProperty("over_all_marks")
	private Double overAllMarks;
	
}
