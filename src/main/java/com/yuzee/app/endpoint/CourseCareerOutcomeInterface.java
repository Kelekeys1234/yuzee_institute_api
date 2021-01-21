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

import com.yuzee.app.dto.CourseCareerOutcomeDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/course/{courseId}/career-outcome")
public interface CourseCareerOutcomeInterface {

	@PostMapping
	public ResponseEntity<?> saveUpdateCourseCareerOutcomes(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@Valid @RequestBody(required = true) final ValidList<CourseCareerOutcomeDto> courseCareerOutcomeDtos)
			throws ValidationException, NotFoundException;

	@DeleteMapping
	public ResponseEntity<?> deleteByCourseCareerOutcomeIds(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@RequestParam(value = "course_career_outcome_ids", required = true) @NotEmpty final List<String> courseCareerOutcomeIds)
			throws ValidationException, NotFoundException, ForbiddenException;
}