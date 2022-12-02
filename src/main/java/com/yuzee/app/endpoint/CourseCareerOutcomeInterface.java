package com.yuzee.app.endpoint;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

@RequestMapping("/api/v1/course/{courseId}/career-outcome")
public interface CourseCareerOutcomeInterface {

	@PostMapping
	public ResponseEntity<?> saveUpdateCourseCareerOutcomes(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@Valid @RequestBody(required = true) final CourseCareerOutcomeRequestWrapper request)
			throws ValidationException, NotFoundException;

	@DeleteMapping
	public ResponseEntity<?> deleteByCourseCareerOutcomeIds(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@RequestParam(value = "course_career_outcome_ids", required = false) @NotEmpty final List<String> courseCareerOutcomeIds,
			@RequestParam(value = "linked_course_ids", required = false) final List<String> linkedCourseIds)
			throws ValidationException, NotFoundException, ForbiddenException;
}