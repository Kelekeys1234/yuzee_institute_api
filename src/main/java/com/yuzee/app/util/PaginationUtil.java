package com.yuzee.app.util;

import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;

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
	public static PaginationUtilDto calculatePagination(final int startIndex, final int pageSize, final int totalCount) {
		PaginationUtilDto paginationUtilDto = new PaginationUtilDto();
		boolean hasPreviousPage = false;
		boolean hasNextPage = false;
		int totalPages = totalCount / pageSize;
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
	
	public static PaginationResponseDto calculatePaginationAndPrepareResponse(final int startIndex, final int pageSize,
			final int totalCount,Object response) {
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		boolean hasPreviousPage = false;
		boolean hasNextPage = false;
		int totalPages = totalCount / pageSize;
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
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setTotalPages(totalPages);
		paginationResponseDto.setPageNumber(pageNumber);
		paginationResponseDto.setHasPreviousPage(hasPreviousPage);
		paginationResponseDto.setHasNextPage(hasNextPage);
		paginationResponseDto.setResponse(response);
		return paginationResponseDto;
	}

	public static int getStartIndex(final int pageNumber, final int pageSize) {
		return (pageNumber - 1) * pageSize;
	}
}
