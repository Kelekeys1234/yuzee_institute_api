package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituteCampusDto {

	@JsonProperty("institute_id")
	private String id;
	
	@JsonProperty("latitude")
	private Double latitude;
	
	@JsonProperty("longitude")
	private Double longitude;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("website")
	private String website;
	
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	@JsonProperty("email")
	private String email;
}
