package com.seeka.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CurrencyRateWrapperDto {
	@JsonProperty("message")
	public String message;

	@JsonProperty("data")
	public CurrencyRateDto data;

	@JsonProperty("status")
	public String status;
}
