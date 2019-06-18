package com.seeka.app.dto;import java.math.BigInteger;



public class JobsDto {
	
	private BigInteger cityId;
	private BigInteger countryId;
	private Integer noOfJobs;
	
	
	public BigInteger getCityId() {
		return cityId;
	}
	public void setCityId(BigInteger cityId) {
		this.cityId = cityId;
	}
	public BigInteger getCountryId() {
		return countryId;
	}
	public void setCountryId(BigInteger countryId) {
		this.countryId = countryId;
	}
	public Integer getNoOfJobs() {
		return noOfJobs;
	}
	public void setNoOfJobs(Integer noOfJobs) {
		this.noOfJobs = noOfJobs;
	} 
	
	
}
