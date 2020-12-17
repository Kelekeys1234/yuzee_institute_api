package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OffCampusCourseDto {

	@JsonProperty("id")
	private String id;

	@JsonProperty("latitude")
	private Double latitude;

	@JsonProperty("longitude")
	private Double longitude;

	@JsonProperty("admin_fee")
	private Double adminFee;

	@JsonProperty("material_fee")
	private Double materialFee;

	@JsonProperty("address")
	private String address;

	@JsonProperty("country_name")
	private String countryName;

	@JsonProperty("city_name")
	private String cityName;

	@JsonProperty("state_name")
	private String stateName;
}
