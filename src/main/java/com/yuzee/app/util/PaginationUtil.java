package com.yuzee.app.util;

import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public static void validatePaginationParameters(final int pageNumber, final int pageSize) throws ValidationException {
		if (pageNumber < 1) {
			log.error("Page number can not be less than 1");
			throw new ValidationException("Page number can not be less than 1");
		}

		if (pageSize < 1) {
			log.error("Page size can not be less than 1");
			throw new ValidationException("Page size can not be less than 1");
		}
	}
}
