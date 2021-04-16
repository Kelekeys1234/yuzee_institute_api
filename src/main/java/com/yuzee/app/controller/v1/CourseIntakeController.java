package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseIntakeRequestWrapper;
import com.yuzee.app.endpoint.CourseIntakeInterface;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.processor.CourseIntakeProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseIntakeController implements CourseIntakeInterface {

	@Autowired
	private CourseIntakeProcessor courseIntakeProcessor;

	@Override
	public ResponseEntity<?> saveAll(String userId, String courseId, @Valid CourseIntakeRequestWrapper request)
			throws ValidationException, NotFoundException {
		log.info("inside CourseIntakeController.saveAll");
		courseIntakeProcessor.saveCourseIntakes(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage("Course Intakes added successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseIntakeIds(String userId, String courseId, List<String> intakeIds,
			List<String> linkedCourseIds) throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseIntakeController.deleteByCourseIntakeIds");
		courseIntakeProcessor.deleteByCourseIntakeIds(userId, courseId, intakeIds, linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Intakes deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
