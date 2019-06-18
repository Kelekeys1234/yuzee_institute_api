package com.seeka.app.dto;import java.math.BigInteger;



public class CourseFilterCostResponseDto {
	
	private BigInteger currencyId; 
	private Long minCost; 
	private Long maxCost; 
	private String currencySymbol;
	private String currencyCode;
	private String currencyName;
	
	
	public BigInteger getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigInteger currencyId) {
		this.currencyId = currencyId;
	}
	 
	public Long getMinCost() {
		if(null == minCost) {
			minCost = 0l;
		}
		return minCost;
	}
	public void setMinCost(Long minCost) {
		this.minCost = minCost;
	}
	public Long getMaxCost() {
		if(null == maxCost) {
			maxCost = 0l;
		}
		return maxCost;
	}
	public void setMaxCost(Long maxCost) {
		this.maxCost = maxCost;
	}
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
}
