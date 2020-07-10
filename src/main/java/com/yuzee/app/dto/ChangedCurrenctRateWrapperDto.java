package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChangedCurrenctRateWrapperDto {

	@JsonProperty("message")
	public String message;

	@JsonProperty("data")
	public List<CurrencyRateDto> data;

	@JsonProperty("status")
	public String status;

}
