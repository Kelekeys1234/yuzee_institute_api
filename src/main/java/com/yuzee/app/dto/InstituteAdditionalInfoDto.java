package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InstituteAdditionalInfoDto {
	
	@NotNull(message = "student_number is required")
	@JsonProperty("student_number")
	private int numberOfStudent;
	
	@NotNull(message = "employee_number is required")
	@JsonProperty("employee_number")
	private int numberOfEmployee;
	
	@NotNull(message = "teacher_number is required")
	@JsonProperty("teacher_number")
	private int numberOfTeacher;
	
	@NotNull(message = "class_number is required")
	@JsonProperty("class_number")
	private int numberOfClassRoom;
	
	@NotEmpty(message = "campus_size is required")
	@JsonProperty("campus_size")
	private String sizeOfCampus;
	
	@NotNull(message = "lecture_hall_number is required")
	@JsonProperty("lecture_hall_number")
	private int numberOfLectureHall;
	
	@NotNull(message = "faculty_number is required")
	@JsonProperty("faculty_number")
	private int numberOfFaculty;
	
	@NotNull(message = "employment_rate is required")
	@JsonProperty("employment_rate")
	private String employmentRate;
}
