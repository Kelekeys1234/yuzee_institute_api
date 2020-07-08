package com.seeka.app.controller.v1;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.service.IEnrollmentService;
import com.seeka.app.util.PaginationUtil;

/**
 *
 * @author SeekADegree
 *
 */
@RestController("enrollmentControllerV1")
@RequestMapping("/api/v1/enrollment")
public class EnrollmentController {

	@Autowired
	private IEnrollmentService iEnrollmentService;

	@PostMapping
	public ResponseEntity<?> addEnrollment(@RequestBody @Valid final EnrollmentDto enrollmentDto, final BindingResult bindingResult)
			throws ValidationException {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		EnrollmentDto resultEnrollmentDto = iEnrollmentService.addEnrollment(enrollmentDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultEnrollmentDto).setMessage("Created enrollment successfully")
				.create();
	}

	@PutMapping("/{enrollmentId}")
	public ResponseEntity<?> updateEnrollment(@PathVariable final String enrollmentId, @RequestBody final EnrollmentDto enrollmentDto)
			throws ValidationException {
		EnrollmentDto resultEnrollmentDto = iEnrollmentService.updateEnrollment(enrollmentDto, enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultEnrollmentDto).setMessage("Updated enrollment successfully")
				.create();
	}

	@PostMapping("/status")
	public ResponseEntity<?> updateEnrollmentStatus(@RequestHeader final String userId, @RequestBody final EnrollmentStatusDto enrollmentStatusDto)
			throws ValidationException {
		EnrollmentStatus enrollmentStatus = iEnrollmentService.updateEnrollmentStatus(enrollmentStatusDto, userId);
		/**
		 * Sent Notification to user regarding status of application.
		 */
		iEnrollmentService.sentEnrollmentNotification(enrollmentStatus, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated enrollment status successfully").create();
	}

	@GetMapping("/{enrollmentId}")
	public ResponseEntity<?> getEnrollmentDetail(@PathVariable final String enrollmentId) throws ValidationException {
		EnrollmentResponseDto enrollmentResponseDto = iEnrollmentService.getEnrollmentDetail(enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(enrollmentResponseDto).setMessage("Get enrollment details successfully")
				.create();
	}

	@GetMapping("/user/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getEnrollmentListBasedOnUserId(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestHeader final String userId, @RequestParam(name = "isArchive", required = false) final boolean isArchive) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<EnrollmentResponseDto> enrollmentResponseList = iEnrollmentService.getEnrollmentList(userId, null, null, null, null, null, startIndex, pageSize,
				isArchive, null, null, null);
		int totalCount = iEnrollmentService.countOfEnrollment(userId, null, null, null, null, null, null);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get enrollment List successfully");
		responseMap.put("data", enrollmentResponseList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getEnrollmentList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final String courseId, @RequestParam(required = false) final String instituteId,
			@RequestParam(required = false) final String enrollmentId, @RequestParam(required = false) final String status,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn,
			@RequestParam(required = false) final String userId, @RequestParam(required = false) final String sortByField,
			@RequestParam(required = false) final String sortByType, @RequestParam(required = false) final String searchKeyword) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<EnrollmentResponseDto> enrollmentResponseList = iEnrollmentService.getEnrollmentList(null, courseId, instituteId, enrollmentId, status, updatedOn,
				startIndex, pageSize, null, sortByField, sortByType, searchKeyword);
		int totalCount = iEnrollmentService.countOfEnrollment(null, courseId, instituteId, enrollmentId, status, updatedOn, searchKeyword);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get enrollment List successfully");
		responseMap.put("data", enrollmentResponseList);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/status/{enrollmentId}")
	public ResponseEntity<?> getEnrollmentStatusDetail(@PathVariable final String enrollmentId) throws ValidationException {
		List<EnrollmentStatusDto> enrollmentStatus = iEnrollmentService.getEnrollmentStatusDetail(enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(enrollmentStatus).setMessage("Get enrollment status details successfully")
				.create();
	}
	
	@GetMapping("/getEnrollmentstatus")
	public ResponseEntity<?> getEnrollmentstatus() {
		Map<String, AtomicLong> enrollmentStatus = iEnrollmentService.getEnrollmentStatus();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(enrollmentStatus).setMessage("Get enrollment status details successfully")
				.create();
		
	}
	
	

}
