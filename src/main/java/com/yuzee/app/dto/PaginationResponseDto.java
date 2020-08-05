package com.yuzee.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginationResponseDto {
	
	private Object response;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
	
	public PaginationResponseDto(Object response, Integer totalCount, Integer pageNumber, Boolean hasPreviousPage,
			Boolean hasNextPage, Integer totalPages) {
		this.response = response;
		this.totalCount = totalCount;
		this.pageNumber = pageNumber;
		this.hasPreviousPage = hasPreviousPage;
		this.hasNextPage = hasNextPage;
		this.totalPages = totalPages;
	}
}
