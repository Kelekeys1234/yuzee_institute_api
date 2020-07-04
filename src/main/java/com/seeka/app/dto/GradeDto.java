package com.seeka.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {

	@JsonProperty("grade_id")
	private String id;
   
	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;
	
	@JsonProperty("education_system_id")
    private String educationSystemId;
	
	@JsonProperty("subject_grades")
    private List<String> subjectGrades;
	
	@JsonProperty("grade")
	@NotBlank(message = "grade should not be blank")
    private String grade;
	
	@JsonProperty("gpa_grade")
	@NotBlank(message = "gpa_grade should not be blank")
	private String gpaGrade;
}
