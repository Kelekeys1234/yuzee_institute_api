package com.yuzee.app.endpoint;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

@RequestMapping("/api/v1/course/{courseId}/min-requirement")
public interface CourseMinRequirementInterface {

	@PostMapping
	public ResponseEntity<Object> saveCourseMinRequirement(
			@RequestHeader(value = "userId") final String userId, @PathVariable final String courseId,
			@Valid @RequestBody(required = true) final CourseMinRequirementDto courseMinRequirementDto)
			throws ValidationException, NotFoundException, ForbiddenException;

	@PutMapping("/{courseMinRequirementId}")
	public ResponseEntity<Object> updateCourseMinRequirement(
			@RequestHeader(value = "userId") final String userId, @PathVariable final String courseId,
			@Valid @RequestBody(required = true) final CourseMinRequirementDto courseMinRequirementDto,
			@PathVariable final String courseMinRequirementId)
			throws ValidationException, NotFoundException, ForbiddenException;

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<Object> getAllCourseMinimumRequirements(
			@RequestHeader(value = "userId") final String userId, @PathVariable final String courseId,
			@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize)
			throws InternalServerException, NotFoundException, ForbiddenException;

	@DeleteMapping("/{courseMinRequirementId}")
	public ResponseEntity<Object> deleteCourseMinRequirement(
			@RequestHeader(value = "userId") final String userId, @PathVariable final String courseId,
			@PathVariable final String courseMinRequirementId,
			@RequestParam(value = "linked_course_ids", required = false) final List<String> linkedCourseIds)
			throws InternalServerException, NotFoundException, ForbiddenException, ValidationException;
}