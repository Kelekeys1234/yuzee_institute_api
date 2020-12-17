package com.yuzee.app.endpoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuzee.app.dto.OffCampusCourseRequestDto;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping(path = "/api/v1/off-campus-course")
public interface OffCampusCourseInterface {

	@PostMapping
	public ResponseEntity<?> save(@RequestHeader("userId") final String userId,
			@Valid @RequestBody final OffCampusCourseRequestDto course)
			throws ValidationException, CommonInvokeException;

	@PutMapping("/{offCampusCourseId}")
	public ResponseEntity<?> update(@RequestHeader("userId") final String userId,
			@PathVariable final String offCampusCourseId, @Valid @RequestBody final OffCampusCourseRequestDto course)
			throws ValidationException, CommonInvokeException;

	@DeleteMapping("/{offCampusCourseId}")
	public ResponseEntity<?> delete(@RequestHeader("userId") final String userId,
			@PathVariable final String offCampusCourseId);

	@GetMapping(value = "/institute/{instituteId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getOffCampusCoursesByInstituteId(@PathVariable final String instituteId,
			@PathVariable Integer pageNumber, @PathVariable Integer pageSize) throws NotFoundException;

	@GetMapping("/{offCampusCourseId}")
	public ResponseEntity<?> getOffCampusCourseById(@PathVariable final String offCampusCourseId)
			throws NotFoundException, ValidationException;
}
