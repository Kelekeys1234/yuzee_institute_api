package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReviewStarDto {

	@JsonProperty("entity_id")
	private String entityId;

	@JsonProperty("review_stars")
	private Double reviewStars;
	
	@JsonProperty("reviews_count")
	private Long reviewsCount;
}
