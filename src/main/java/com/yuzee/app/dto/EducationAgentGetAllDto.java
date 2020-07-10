package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EducationAgentGetAllDto {

	@JsonProperty("education_agent_id")
    private String id;
    
	@JsonProperty("last_updated")
	private String lastUpdated;
	
	@JsonProperty("agent_name")
    private String agentName;
	
	@JsonProperty("description")
    private String description;
	
	@JsonProperty("city")
    private String city;
	
	@JsonProperty("country")
    private String country;
	
	@JsonProperty("contact")
    private String contact;
	
	@JsonProperty("status")
    private String status;
}
