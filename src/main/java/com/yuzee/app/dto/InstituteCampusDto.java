package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface InstituteCampusDto {

	@JsonProperty("institute_id")
	String getId();
	
	@JsonProperty("latitude")
	Double getLatitude();
	
	@JsonProperty("longitude")
	Double getLongitude();
	
	@JsonProperty("address")
	String getAddress();
	
	@JsonProperty("website")
	String getWebsite();
	
	@JsonProperty("phone_number")
	String getPhoneNumber();
	
	@JsonProperty("email") 
	String getEmail();
	
}
