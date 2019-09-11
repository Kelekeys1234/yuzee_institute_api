package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IEnrollmentService;

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

	@GetMapping
	public ResponseEntity<?> getEnrollmentList() throws ValidationException {
		List<EnrollmentResponseDto> enrollmentResponseList = iEnrollmentService.getEnrollmentList();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(enrollmentResponseList).setMessage("Get enrollment List successfully")
				.create();
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
