package com.yuzee.app.dto;

import lombok.Data;

@Data
public class PaginationResponseDto {
	private Object response;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
}
