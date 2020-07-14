package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NearestInstituteDTO  {
	
	@JsonProperty("nearest_institutes")
	private List<InstituteResponseDto> nearestInstitutes;
	
	@JsonProperty("total_count")
	private Integer totalCount;
	
	@JsonProperty("page_number")
	private Integer pageNumber;
	
	@JsonProperty("has_previous_page")
	private Boolean hasPreviousPage;
	
	@JsonProperty("has_next_page")
	private Boolean hasNextPage;
	
	@JsonProperty("total_pages")
	private Integer totalPages;
}
