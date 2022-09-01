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

import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

@RequestMapping("/api/v1")
public interface CourseFundingInterface {

	@PostMapping("/course/funding/instituteId/{instituteId}/add-funding-to-all-courses")
	public ResponseEntity<?> addFundingToAllInstituteCourses(
			@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String instituteId, @Valid @RequestBody final List<String> fundingNameId)
			throws ValidationException, NotFoundException, InvokeException;

	@PostMapping("/course/{courseId}/funding")
	public ResponseEntity<?> saveAll(@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String courseId,
			 @RequestBody(required = true) final CourseFundingRequestWrapper request)
			throws ValidationException, NotFoundException, InvokeException ;

	@DeleteMapping("/course/{courseId}/funding")
	public ResponseEntity<?> deleteByFundingNameIds(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@RequestParam(value = "funding_name_ids", required = true) final List<String> fundingNameIds,
			@RequestParam(value = "linked_course_ids", required =false) final List<String> linkedCourseIds)
			throws ValidationException, NotFoundException, ForbiddenException;
}