package com.yuzee.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
	
	public NearestInstituteDTO(List<InstituteResponseDto> nearestInstitutes, Integer totalCount, Integer pageNumber, Boolean hasPreviousPage,
			Boolean hasNextPage, Integer totalPages) {
		this.nearestInstitutes = nearestInstitutes;
		this.totalCount = totalCount;
		this.pageNumber = pageNumber;
		this.hasPreviousPage = hasPreviousPage;
		this.hasNextPage = hasNextPage;
		this.totalPages = totalPages;
	}
}
