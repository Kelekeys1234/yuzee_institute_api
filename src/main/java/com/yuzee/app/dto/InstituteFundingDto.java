package com.yuzee.app.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteFundingDto {

	@JsonProperty(value = "funding_name_id")
	@NotEmpty(message = "{funding_name_id.is_required}")
	private String fundingNameId;
}
