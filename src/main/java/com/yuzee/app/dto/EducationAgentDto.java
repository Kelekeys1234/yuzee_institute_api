package com.yuzee.app.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EducationAgentDto {

	@JsonProperty("first_name")
	@NotBlank(message = "first_name should not be blank")
	private String firstName;
	
	@JsonProperty("last_name")
	@NotBlank(message = "last_name should not be blank")
	private String lastName;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("city")
	@NotBlank(message = "city should not be blank")
	private String city;
	
	@JsonProperty("country")
	@NotBlank(message = "country should not be blank")
	private String country;
	
	@JsonProperty("phone_number")
	@NotBlank(message = "phone_number should not be blank")
	private String phoneNumber;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("skill")
	private List<String> skill;
	
	@JsonProperty("accomplishment")
	private List<String> accomplishment;
	
	@JsonProperty("agent_service_offereds")
	private List<AgentServiceOfferedDto> agentServiceOffereds;
	
	@JsonProperty("agent_education_details")
	private List<AgentEducationDetailDto> agentEducationDetails;
}