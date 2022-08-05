package com.yuzee.app.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseIntakeDto {

	@JsonProperty("course_intake_id")
	private String id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value = "intake_date")
	@NotNull(message = "{intake_date.is_required}")
	private Date intakeDate;
}
