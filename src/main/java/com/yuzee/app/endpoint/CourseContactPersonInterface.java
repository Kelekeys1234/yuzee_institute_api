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

import com.yuzee.app.dto.CourseContactPersonRequestWrapper;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;

@RequestMapping("/api/v1/course/{courseId}/contact-person")
public interface CourseContactPersonInterface {

	@PostMapping
	public ResponseEntity<?> saveAll(@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String courseId,
			@Valid @RequestBody(required = true) final CourseContactPersonRequestWrapper request)
			throws ValidationException, NotFoundException, InvokeException;

	@DeleteMapping
	public ResponseEntity<?> deleteByCourseAndUserIds(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@RequestParam(value = "user_ids", required = true) final List<String> userIds,
			@RequestParam(value = "linked_course_ids", required = false) final List<String> linkedCourseIds)
			throws ValidationException, NotFoundException, ForbiddenException;
}