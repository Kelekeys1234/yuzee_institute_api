package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.service.IErrorReportService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/error/report")
public class ErrorReportController {

	@Autowired
	private IErrorReportService errorReportService;

	@RequestMapping(value = "/category/{errorCategoryType}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllErrorCategory(@PathVariable final String errorCategoryType) throws Exception {
		return errorReportService.getAllErrorCategory(errorCategoryType);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> save(@Valid @RequestBody final ErrorReportDto errorReport) throws Exception {
		return errorReportService.save(errorReport);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> update(@Valid @RequestBody final ErrorReportDto errorReport, @Valid @PathVariable final BigInteger id) throws Exception {
		return errorReportService.update(errorReport, id);
	}

	/*
	 * @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET,
	 * produces = "application/json") public ResponseEntity<?>
	 * getErrorReportByUserId(@PathVariable BigInteger userId) throws Exception {
	 * return errorReportService.getErrorReportByUserId(userId); }
	 */

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getErrorReportById(@PathVariable final BigInteger id) throws Exception {
		return errorReportService.getErrorReportById(id);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> get() throws Exception {
		return errorReportService.getAllErrorReport();
	}

	@GetMapping(value = "/user/pageNumber/{pageNumber}/pageSize/{pageSize}", produces = "application/json")
	public ResponseEntity<?> getErrorReportForUser(@RequestHeader(value = "userId") final BigInteger userId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ErrorReport> errorReportList = errorReportService.getAllErrorReportForUser(userId, startIndex, pageSize);
		int totalCount = errorReportService.getErrorReportCount(userId);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Error Report successfully");
		responseMap.put("data", errorReportList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@Valid @PathVariable final BigInteger userId) throws Exception {
		return ResponseEntity.accepted().body(errorReportService.deleteByUserId(userId));
	}

	@PutMapping(value = "/{errorReportId}/isFavourite/{isFavourite}")
	public ResponseEntity<?> setIsFavourite(@RequestHeader(value = "userId") final BigInteger userId,
			@PathVariable(value = "errorReportId") final BigInteger errorReportId, @PathVariable(value = "isFavourite") final boolean isFavourite)
			throws NotFoundException {
		errorReportService.setIsFavouriteFlag(errorReportId, isFavourite);
		return new GenericResponseHandlers.Builder().setMessage("Updated successfuly").setStatus(HttpStatus.OK).create();
	}
}
