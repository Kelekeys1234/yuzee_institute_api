package com.seeka.app.dto;

public class CurrencyConverterDto {
	
	private String fromCurrencyId;
	private String toCurrencyId;
	private String fromCurrencyCode;
	private String toCurrencyCode;
	private Double conversionRate;
	private String fromCurrencyMax;
	private String toCurrencyMax;
	
	public String getFromCurrencyId() {
		return fromCurrencyId;
	}
	public void setFromCurrencyId(String fromCurrencyId) {
		this.fromCurrencyId = fromCurrencyId;
	}
	public String getToCurrencyId() {
		return toCurrencyId;
	}
	public void setToCurrencyId(String toCurrencyId) {
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
	public String getFromCurrencyMax() {
		return fromCurrencyMax;
	}
	public void setFromCurrencyMax(String fromCurrencyMax) {
		this.fromCurrencyMax = fromCurrencyMax;
	}
	public String getToCurrencyMax() {
		return toCurrencyMax;
	}
	public void setToCurrencyMax(String toCurrencyMax) {
		this.toCurrencyMax = toCurrencyMax;
	}
	
}
