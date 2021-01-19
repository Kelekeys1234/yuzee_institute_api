package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseDeliveryModesDto {

	@JsonProperty("delivery_mode_id")
	private String id;

	@JsonProperty("delivery_type")
	@NotEmpty(message = "delivery_type should not be blank")
	private String deliveryType;

	@JsonProperty("duration")
	private Double duration;

	@JsonProperty("duration_time")
	private String durationTime;

	@JsonProperty("domestic_fee")
	@NotNull(message = "domestic_fee should not be blank")
	private Double domesticFee;

	@JsonProperty("international_fee")
	@NotNull(message = "international_fee should not be blank")
	private Double internationalFee;

	@JsonProperty("study_mode")
	private String studyMode;

	@JsonProperty("usd_domestic_fee")
	private Double usdDomesticFee;

	@JsonProperty("usd_international_fee")
	private Double usdInternationalFee;

	@JsonProperty("course_id")
	private String courseId;
}
