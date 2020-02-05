package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.InstituteGoogleReviewDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.service.IInstituteGoogleReviewService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/review/google")
public class InstituteGoogleReviewController {

	@Autowired
	private IInstituteGoogleReviewService iInstituteGoogleReviewService;

	/**
	 *
	 * @param instituteId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/{instituteId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getInstituteGoogleReview(@PathVariable final BigInteger instituteId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<InstituteGoogleReviewDto> instituteGoogleReviewList = iInstituteGoogleReviewService.getInstituteGoogleReview(instituteId, startIndex, pageSize);
		int totalCount = iInstituteGoogleReviewService.getCountInstituteGoogleReview(instituteId);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Institute google review successfully");
		responseMap.put("data", instituteGoogleReviewList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	/**
	 *
	 * @param instituteId
	 * @return
	 */
	@GetMapping("/average/{instituteId}")
	public ResponseEntity<Object> getInstituteAvgGoogleReview(@PathVariable final String instituteId) {
		Double rating = iInstituteGoogleReviewService.getInstituteAvgGoogleReview(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(rating).setMessage("Get Institute average rating successfully").create();
	}

}
