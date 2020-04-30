package com.seeka.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituteContactInfoDto {

	@NotNull(message = "latitute is required")
	@JsonProperty("latitute")
	private Double latitute;
	
	@NotNull(message = "longitude is required")
	@JsonProperty("longitude")
	private Double longitude;
	
	@NotEmpty(message = "address is required")
	@JsonProperty("address")
	private String address;
	
	@NotEmpty(message = "office_hours_from is required")
	@JsonProperty("office_hours_from")
	private String officeHoursFrom;
	
	@NotEmpty(message = "office_hours_to is required")
	@JsonProperty("office_hours_to")
	private String officeHoursTo;
	
	@NotEmpty(message = "website is required")
	@JsonProperty("website")
	private String website;
	
	@NotEmpty(message = "contact_number is required")
	@JsonProperty("contact_number")
	private String contactNumber;
	
	@NotEmpty(message = "eamil is required")
	@JsonProperty("eamil")
	private String email;
	
	@NotEmpty(message = "average_living_cost is required")
	@JsonProperty("average_living_cost")
	private String averagelivingCost;
	
}
