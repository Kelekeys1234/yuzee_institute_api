package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayTimingDto {

	@JsonProperty("opening_from")
	private String openingFrom;

	@JsonProperty("opening_to")
	private String openingTo;

	@JsonProperty("day")
	private String day;
}
