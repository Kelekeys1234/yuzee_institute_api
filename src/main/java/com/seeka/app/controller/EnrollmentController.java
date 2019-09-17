package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IEnrollmentService;
import com.seeka.app.util.PaginationUtil;

/**
 *
 * @author SeekADegree
 *
 */
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

	@Autowired
	private IEnrollmentService iEnrollmentService;

	@PostMapping
	public ResponseEntity<?> addEnrollment(@RequestBody final EnrollmentDto enrollmentDto) throws ValidationException {
		EnrollmentDto resultEnrollmentDto = iEnrollmentService.addEnrollment(enrollmentDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultEnrollmentDto).setMessage("Created enrollment successfully")
				.create();
	}

	@PutMapping("/{enrollmentId}")
	public ResponseEntity<?> updateEnrollment(@PathVariable final BigInteger enrollmentId, @RequestBody final EnrollmentDto enrollmentDto)
			throws ValidationException {
		EnrollmentDto resultEnrollmentDto = iEnrollmentService.updateEnrollment(enrollmentDto, enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultEnrollmentDto).setMessage("Updated enrollment successfully")
				.create();
	}

	@PostMapping("/status")
	public ResponseEntity<?> updateEnrollmentStatus(@RequestBody final EnrollmentStatusDto enrollmentStatusDto) throws ValidationException {
		iEnrollmentService.updateEnrollmentStatus(enrollmentStatusDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated enrollment status successfully").create();
	}

	@GetMapping("/{enrollmentId}")
	public ResponseEntity<?> getEnrollmentDetail(@PathVariable final BigInteger enrollmentId) throws ValidationException {
		EnrollmentResponseDto enrollmentResponseDto = iEnrollmentService.getEnrollmentDetail(enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(enrollmentResponseDto).setMessage("Get enrollment details successfully")
				.create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getEnrollmentList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
			@RequestParam(required = false) final BigInteger courseId, @RequestParam(required = false) final BigInteger instituteId,
			@RequestParam(required = false) final BigInteger enrollmentId, @RequestParam(required = false) final String status,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date updatedOn) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<EnrollmentResponseDto> enrollmentResponseList = iEnrollmentService.getEnrollmentList(courseId, instituteId, enrollmentId, status, updatedOn,
				startIndex, pageSize);
		int totalCount = iEnrollmentService.countOfEnrollment();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(4);
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
	public ResponseEntity<?> getEnrollmentStatusDetail(@PathVariable final BigInteger enrollmentId) throws ValidationException {
		List<EnrollmentStatusDto> enrollmentStatus = iEnrollmentService.getEnrollmentStatusDetail(enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(enrollmentStatus).setMessage("Get enrollment status details successfully")
				.create();
	}

	@DeleteMapping("/image/{enrollmentImageId}")
	public ResponseEntity<?> removeEnrollmentImage(@PathVariable final BigInteger enrollmentImageId) throws ValidationException {
		iEnrollmentService.removeEnrollmentImage(enrollmentImageId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted enrollment image successfully").create();
	}

}
