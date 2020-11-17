package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class InstituteAdditionalInfoDto {
	
	@NotNull(message = "student_number is required")
	@JsonProperty("student_number")
	private Integer numberOfStudent;
	
	@NotNull(message = "employee_number is required")
	@JsonProperty("employee_number")
	private Integer numberOfEmployee;
	
	@NotNull(message = "teacher_number is required")
	@JsonProperty("teacher_number")
	private Integer numberOfTeacher;
	
	@NotNull(message = "class_number is required")
	@JsonProperty("class_number")
	private Integer numberOfClassRoom;
	
	@NotNull(message = "campus_size is required")
	@JsonProperty("campus_size")
	private Integer sizeOfCampus;
	
	@NotNull(message = "lecture_hall_number is required")
	@JsonProperty("lecture_hall_number")
	private Integer numberOfLectureHall;
	
	@NotNull(message = "faculty_number is required")
	@JsonProperty("faculty_number")
	private Integer numberOfFaculty;
	
	@NotNull(message = "employment_rate is required")
	@JsonProperty("employment_rate")
	private Integer rateOfEmployment;

	@NotBlank(message = "about_info should not be blank")
	@JsonProperty(value = "about_info", access = Access.WRITE_ONLY)
	private String aboutInfo;
}
