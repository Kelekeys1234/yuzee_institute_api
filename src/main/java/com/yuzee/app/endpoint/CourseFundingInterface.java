package com.yuzee.app.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.app.dto.CourseFundingDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1")
public interface CourseFundingInterface {

	@PostMapping("/course/funding/instituteId/{instituteId}/add-funding-to-all-courses")
	public ResponseEntity<?> addFundingToAllInstituteCourses(
			@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String instituteId, @Valid @RequestBody final CourseFundingDto courseFundingDto)
			throws ValidationException, NotFoundException;

	@PostMapping("/course/{courseId}/funding")
	public ResponseEntity<?> saveAll(@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String courseId,
			@Valid @RequestBody(required = true) final ValidList<CourseFundingDto> courseFundingDto)
			throws ValidationException, NotFoundException;

	@DeleteMapping("/course/{courseId}/funding")
	public ResponseEntity<?> deleteByFundingNameIds(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@RequestParam(value = "funding_name_ids", required = true) final List<String> fundingNameIds)
			throws ValidationException, NotFoundException, ForbiddenException;
}