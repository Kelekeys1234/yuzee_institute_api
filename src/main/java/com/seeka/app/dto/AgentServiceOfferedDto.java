package com.seeka.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AgentServiceOfferedDto {

	@JsonProperty("service")
	@NotBlank(message = "service should not be blank")
    private String service;
	
	@JsonProperty("amount")
	@NotNull(message = "amount should not be blank")
    private Double amount;
	
	@JsonProperty("currency")
	@NotBlank(message = "currency should not be blank")
    private String currency;
}
