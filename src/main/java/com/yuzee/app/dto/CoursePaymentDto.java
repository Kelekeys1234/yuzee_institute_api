package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CoursePaymentDto {
	@JsonProperty("course_payment_id")
	private String id;

	@JsonProperty("description")
	@NotEmpty(message = "description should not be blank")
	private String description;

	@Valid
	@JsonProperty("payment_items")
	@NotEmpty(message = "payment_items must not be empty")
	private ValidList<CoursePaymentItemDto> paymentItems = new ValidList<>();

	@NotNull(message = "linked_course_ids must not be null")
	@JsonProperty("linked_course_ids")
	List<String> linkedCourseIds;
}
