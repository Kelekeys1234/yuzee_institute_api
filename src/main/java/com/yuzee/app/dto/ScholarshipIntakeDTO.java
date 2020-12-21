package com.yuzee.app.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScholarshipIntakeDTO {

	@JsonProperty("intake_id")
	private String id;

	@JsonProperty("student_category")
	@NotBlank(message = "student_category should not be blank")
	private String studentCategory;

	@NotNull(message = "intake_date should not be blank")
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("intake_date")
	private Date intakeDate;

	@NotNull(message = "intake_deadline should not be blank")
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty("intake_deadline")
	private Date deadline;
}
