package com.seeka.app.dto;

public class JobsDto {
	
	private String cityId;
	private String countryId;
	private Integer noOfJobs;
	
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public Integer getNoOfJobs() {
		return noOfJobs;
	}
	public void setNoOfJobs(Integer noOfJobs) {
		this.noOfJobs = noOfJobs;
	} 
	
	
}
