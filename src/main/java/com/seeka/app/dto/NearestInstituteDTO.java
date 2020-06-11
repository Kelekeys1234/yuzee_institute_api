package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class NearestInstituteDTO  {
	private List<InstituteResponseDto> nearestInstitutes;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
}
