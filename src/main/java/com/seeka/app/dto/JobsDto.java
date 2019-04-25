package com.seeka.app.dto;

import java.util.UUID;

public class JobsDto {
	
	private UUID cityId;
	private UUID countryId;
	private Integer noOfJobs;
	
	
	public UUID getCityId() {
		return cityId;
	}
	public void setCityId(UUID cityId) {
		this.cityId = cityId;
	}
	public UUID getCountryId() {
		return countryId;
	}
	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}
	public Integer getNoOfJobs() {
		return noOfJobs;
	}
	public void setNoOfJobs(Integer noOfJobs) {
		this.noOfJobs = noOfJobs;
	} 
	
	
}
