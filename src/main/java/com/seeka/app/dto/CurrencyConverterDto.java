package com.seeka.app.dto;

import java.util.UUID;

public class CurrencyConverterDto {
	
	private UUID fromCurrencyId;
	private UUID toCurrencyId;
	private String fromCurrencyCode;
	private String toCurrencyCode;
	private Double conversionRate;
	private Integer fromCurrencyMax;
	private Integer toCurrencyMax;
	
	public UUID getFromCurrencyId() {
		return fromCurrencyId;
	}
	public void setFromCurrencyId(UUID fromCurrencyId) {
		this.fromCurrencyId = fromCurrencyId;
	}
	public UUID getToCurrencyId() {
		return toCurrencyId;
	}
	public void setToCurrencyId(UUID toCurrencyId) {
		this.toCurrencyId = toCurrencyId;
	}
	public String getFromCurrencyCode() {
		return fromCurrencyCode;
	}
	public void setFromCurrencyCode(String fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}
	public String getToCurrencyCode() {
		return toCurrencyCode;
	}
	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}
	public Double getConversionRate() {
		return conversionRate;
	}
	public void setConversionRate(Double conversionRate) {
		this.conversionRate = conversionRate;
	}
	public Integer getFromCurrencyMax() {
		return fromCurrencyMax;
	}
	public void setFromCurrencyMax(Integer fromCurrencyMax) {
		this.fromCurrencyMax = fromCurrencyMax;
	}
	public Integer getToCurrencyMax() {
		return toCurrencyMax;
	}
	public void setToCurrencyMax(Integer toCurrencyMax) {
		this.toCurrencyMax = toCurrencyMax;
	}
	
}
