package com.yuzee.app.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FundingResponseDto {
	
	@JsonProperty("funding_id")
	private String id;

	@JsonProperty("funding_name")
	private String fundingName;

	@JsonProperty("funding_description")
	private String fundingDescription;

	@JsonProperty("website_link")
	private String websiteLink;

	@JsonProperty("funding_name_id")
	private String fundingNameId;

	@JsonProperty("funding_nationalitys")
	List<FundingEligibleNationalityDto> listOfFundingNationality = new ArrayList<>();

	public FundingResponseDto(String id, String fundingName, String fundingDescription, String websiteLink,
			String fundingNameId) {
		super();
		this.id = id;
		this.fundingName = fundingName;
		this.fundingDescription = fundingDescription;
		this.websiteLink = websiteLink;
		this.fundingNameId = fundingNameId;
	}
}
