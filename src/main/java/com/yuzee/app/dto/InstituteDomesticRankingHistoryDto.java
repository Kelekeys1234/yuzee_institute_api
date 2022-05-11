package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteDomesticRankingHistoryDto {

	@JsonProperty("domestic_ranking")
	private Integer domesticRanking;

	@JsonProperty("institute_name")
	private String instituteName;
}
