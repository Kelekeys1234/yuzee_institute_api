package com.seeka.app.dto;

import lombok.Data;

@Data
public class PaginationResponseDto {
	private Object institutes;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
}
