package com.seeka.app.controller.v1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.AuditErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.dto.ErrorReportCategoryDto;
import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.dto.ErrorReportResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.service.IErrorReportService;
import com.seeka.app.util.PaginationUtil;

@RestController("errorReportControllerV1")
@RequestMapping("/api/v1/error/report")
public class ErrorReportController {

	@Autowired
	private IErrorReportService errorReportService;

	@PostMapping("/category")
	public ResponseEntity<?> save(@Valid @RequestBody final ErrorReportCategoryDto errorReportCategoryDto, final BindingResult bindingResult)
			throws ValidationException {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		errorReportService.saveErrorReportCategory(errorReportCategoryDto);
		return new GenericResponseHandlers.Builder().setMessage("Create Error Report Category Successfully.").setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/category/{errorCategoryType}")
	public ResponseEntity<?> getAllErrorCategory(@PathVariable final String errorCategoryType) throws Exception {
		List<ErrorReportCategory> errorReportCategories = errorReportService.getAllErrorCategory(errorCategoryType);
		return new GenericResponseHandlers.Builder().setMessage("Get Error Report Category List Successfully").setData(errorReportCategories)
				.setStatus(HttpStatus.OK).create();
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody final ErrorReportDto errorReport, final BindingResult bindingResult) throws ValidationException {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		errorReportService.save(errorReport);
		return new GenericResponseHandlers.Builder().setMessage("Create Error Report Successfully.").setStatus(HttpStatus.OK).create();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody final ErrorReportDto errorReport, @PathVariable final String id) throws ValidationException {
		errorReportService.update(errorReport, id);
		return new GenericResponseHandlers.Builder().setMessage("Update Error Report Successfully.").setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getErrorReportById(@PathVariable final String id) throws ValidationException {
		ErrorReportResponseDto errorReport = errorReportService.getErrorReportById(id);
		return new GenericResponseHandlers.Builder().setMessage("Get Error Report Successfully.").setData(errorReport).setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getAllErrorReport(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String errorReportCategoryId,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn,
			@RequestParam(required = false) final String errorReportStatus, @RequestParam(required = false) final Boolean isFavourite,
			@RequestParam(required = false) final String sortByField, @RequestParam(required = false) final String sortByType,
			@RequestParam(required = false) final String searchKeyword) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ErrorReportResponseDto> errorReports = errorReportService.getAllErrorReport(null, startIndex, pageSize, errorReportCategoryId, errorReportStatus,
				updatedOn, isFavourite, null, sortByField, sortByType, searchKeyword);
		int totalCount = errorReportService.getErrorReportCount(null, errorReportCategoryId, errorReportStatus, updatedOn, isFavourite, null, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Error Report List successfully");
		responseMap.put("data", errorReports);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping(value = "/user/pageNumber/{pageNumber}/pageSize/{pageSize}", produces = "application/json")
	public ResponseEntity<?> getErrorReportForUser(@RequestHeader(value = "userId") final String userId, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam(required = false) final Boolean isFavourite,
			@RequestParam(required = false) final boolean isArchive) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<ErrorReportResponseDto> errorReportList = errorReportService.getAllErrorReport(userId, startIndex, pageSize, null, null, null, isFavourite,
				isArchive, null, null, null);
		int totalCount = errorReportService.getErrorReportCount(userId, null, null, null, isFavourite, isArchive, null);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Error Report list successfully");
		responseMap.put("data", errorReportList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> delete(@Valid @PathVariable final String userId) throws Exception {
		return ResponseEntity.accepted().body(errorReportService.deleteByUserId(userId));
	}

	@DeleteMapping("/{errorReportId}")
	public ResponseEntity<?> deleteErrorReport(@PathVariable final String errorReportId) throws Exception {
		errorReportService.deleteByErrorReportId(errorReportId);
		return new GenericResponseHandlers.Builder().setMessage("Error report deleted successfuly").setStatus(HttpStatus.OK).create();
	}

	@PutMapping(value = "/{errorReportId}/isFavourite/{isFavourite}")
	public ResponseEntity<?> setIsFavourite(@RequestHeader(value = "userId") final String userId,
			@PathVariable(value = "errorReportId") final String errorReportId, @PathVariable(value = "isFavourite") final boolean isFavourite)
			throws NotFoundException {
		errorReportService.setIsFavouriteFlag(errorReportId, isFavourite);
		return new GenericResponseHandlers.Builder().setMessage("Updated successfuly").setStatus(HttpStatus.OK).create();
	}

	@GetMapping("/audit/{id}")
	public ResponseEntity<?> getErrorReportHistoryById(@PathVariable(name = "id") final String errorReportId) throws ValidationException {
		List<AuditErrorReport> errorReport = errorReportService.getAuditListByErrorReport(errorReportId);
		return new GenericResponseHandlers.Builder().setMessage("Get Error Report Audit list Successfully.").setData(errorReport).setStatus(HttpStatus.OK)
				.create();
	}
}
