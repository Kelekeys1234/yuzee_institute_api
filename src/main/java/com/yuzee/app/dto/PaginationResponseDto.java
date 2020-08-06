package com.yuzee.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponseDto {
	
	private Object response;
	private Integer totalCount;
	private Integer pageNumber;
	private Boolean hasPreviousPage;
	private Boolean hasNextPage;
	private Integer totalPages;
}
