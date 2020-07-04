package com.seeka.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CurrencyRateDto {

	@JsonProperty("currency_rate_id")
	private String id;

	@JsonProperty("from_currency_code")
	private String fromCurrencyCode;

	@JsonProperty("to_currency_code")
	private String toCurrencyCode;

	@JsonProperty("conversion_rate")
	private Double conversionRate;

	@JsonProperty("from_currency_name")
	private String fromCurrencyName;

	@JsonProperty("to_currency_name")
	private String toCurrencyName;

	@JsonProperty("has_changed")
	private Boolean hasChanged;

}
