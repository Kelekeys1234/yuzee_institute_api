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

import com.yuzee.app.dto.CoursePaymentDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1")
public interface CoursePaymentInterface {

	@PostMapping("/course/{courseId}/payment")
	public ResponseEntity<?> saveUpdateCoursePayment(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@Valid @RequestBody(required = true) final CoursePaymentDto coursePaymentDto)
			throws ValidationException, NotFoundException, ForbiddenException;

	@DeleteMapping("/course/{courseId}/payment")
	public ResponseEntity<?> deleteCoursePayment(@RequestHeader(value = "userId", required = true) final String userId,
			@PathVariable final String courseId,
			@RequestParam(value = "linked_course_ids", required = false) final List<String> linkedCourseIds)
			throws InternalServerException, NotFoundException, ForbiddenException;
}