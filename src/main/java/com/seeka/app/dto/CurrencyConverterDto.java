package com.seeka.app.dto;import java.math.BigInteger;



public class CurrencyConverterDto {
	
	private BigInteger fromCurrencyId;
	private BigInteger toCurrencyId;
	private String fromCurrencyCode;
	private String toCurrencyCode;
	private Double conversionRate;
	private BigInteger fromCurrencyMax;
	private BigInteger toCurrencyMax;
	
	public BigInteger getFromCurrencyId() {
		return fromCurrencyId;
	}
	public void setFromCurrencyId(BigInteger fromCurrencyId) {
		this.fromCurrencyId = fromCurrencyId;
	}
	public BigInteger getToCurrencyId() {
		return toCurrencyId;
	}
	public void setToCurrencyId(BigInteger toCurrencyId) {
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
	public BigInteger getFromCurrencyMax() {
		return fromCurrencyMax;
	}
	public void setFromCurrencyMax(BigInteger fromCurrencyMax) {
		this.fromCurrencyMax = fromCurrencyMax;
	}
	public BigInteger getToCurrencyMax() {
		return toCurrencyMax;
	}
	public void setToCurrencyMax(BigInteger toCurrencyMax) {
		this.toCurrencyMax = toCurrencyMax;
	}
	
}
