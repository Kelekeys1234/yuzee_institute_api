package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScholarshipDto {

	@JsonProperty("scholarship_id")
	private String id;

	@JsonProperty("name")
	@NotBlank(message = "name should not be blank")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("scholarship_award")
	private String scholarshipAward;

	@JsonProperty("country_name")
	@NotBlank(message = "country_name should not be blank")
	private String countryName;

	@JsonProperty("number_of_avaliability")
	private Integer numberOfAvaliability;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("scholarship_amount")
	private Double scholarshipAmount;

	@JsonProperty("validity")
	private String validity;

	@JsonProperty("how_to_apply")
	private String howToApply;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("eligible_nationalities")
	private List<String> eligibleNationalities;

	@JsonProperty("website")
	private String website;

	@JsonProperty("benefits")
	private String benefits;

	@JsonProperty("requirements")
	private String requirements;

	@JsonProperty("conditions")
	private String conditions;

	@JsonProperty("successful_canidates")
	private String successfulCanidates;

	@Valid
	@JsonProperty("intakes")
	private List<ScholarshipIntakeDto> intakes;

	@JsonProperty("languages")
	private List<String> languages;
}
