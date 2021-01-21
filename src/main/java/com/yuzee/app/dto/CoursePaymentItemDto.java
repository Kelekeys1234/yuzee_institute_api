package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CoursePaymentItemDto {
	@JsonProperty("course_payment_item_id")
	private String id;

	@JsonProperty("name")
	@NotEmpty(message = "name should not be blank")
	private String name;

	@JsonProperty("amount")
	@NotNull(message = "amount should not be null")
	private Double amount;
}
