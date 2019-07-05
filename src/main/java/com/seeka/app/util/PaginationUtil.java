package com.seeka.app.util;

import com.seeka.app.dto.PaginationUtilDto;

public class PaginationUtil {
	public static Integer courseResultPageMaxSize = 50;

	/**
	 * Use to calculate pagination parameters
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @param totalCount
	 * @return
	 */
	public static PaginationUtilDto calculatePagination(int startIndex, int pageSize, int totalCount) {
		PaginationUtilDto paginationUtilDto = new PaginationUtilDto();
		boolean hasPreviousPage = false;
		boolean hasNextPage = false;
		int totalPages = (totalCount / pageSize);
		if (totalCount % pageSize > 0) {
			totalPages += 1;
		}
		int pageNumber = startIndex / pageSize + 1;

		if (pageNumber != 1 && pageNumber <= totalPages) {
			hasPreviousPage = true;
		}
		if (pageNumber < totalPages) {
			hasNextPage = true;
		}
		paginationUtilDto.setTotalPages(totalPages);
		paginationUtilDto.setPageNumber(pageNumber);
		paginationUtilDto.setHasPreviousPage(hasPreviousPage);
		paginationUtilDto.setHasNextPage(hasNextPage);
		return paginationUtilDto;

	}
}
