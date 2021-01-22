package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundingEligibleNationalityDto {
	
	@JsonProperty("is_discount")
	private boolean isDiscount;
	
	@JsonProperty("is_loan")
	private boolean isLoan;
	
	@JsonProperty("tution_coverage")
	private String tutionCoverage;
	
	@JsonProperty("available_nationality")
	private String availableNationality;

}
