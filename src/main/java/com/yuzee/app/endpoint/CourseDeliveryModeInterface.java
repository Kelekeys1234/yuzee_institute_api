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

import com.yuzee.app.dto.CourseDeliveryModesDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;

@RequestMapping("/api/v1/course/{courseId}/delivery-mode")
public interface CourseDeliveryModeInterface {

	@PostMapping
	public ResponseEntity<?> saveUpdateCourseDeliveryMode(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@Valid @RequestBody(required = true) final ValidList<CourseDeliveryModesDto> courseDeliveryModeDtos)
			throws ValidationException, NotFoundException, InternalServerException, CommonInvokeException;

	@DeleteMapping
	public ResponseEntity<?> deleteByCourseDeliveryModeIds(
			@RequestHeader(value = "userId", required = true) final String userId, @PathVariable final String courseId,
			@RequestParam(value = "course_delivery_mode_ids", required = true) @NotEmpty final List<String> courseDeliveryModeIds)
			throws ValidationException, NotFoundException, ForbiddenException;
}