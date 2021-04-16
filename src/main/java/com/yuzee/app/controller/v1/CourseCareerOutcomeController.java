package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseCareerOutcomeRequestWrapper;
import com.yuzee.app.endpoint.CourseCareerOutcomeInterface;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.processor.CourseCareerOutcomeProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseCareerOutcomeController implements CourseCareerOutcomeInterface {

	@Autowired
	private CourseCareerOutcomeProcessor courseCareerOutcomeProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseCareerOutcomes(String userId, String courseId,
			@Valid CourseCareerOutcomeRequestWrapper request) throws ValidationException, NotFoundException {
		log.info("inside CourseCareerOutcomeController.saveUpdateCourseCareerOutcomes");
		courseCareerOutcomeProcessor.saveUpdateCourseCareerOutcomes(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage("Course CareerOutcomes added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseCareerOutcomeIds(String userId, String courseId,
			List<String> courseCareerOutcomeIds, List<String> linkedCourseIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseCareerOutcomeController.deleteByCourseCareerOutcomeIds");
		courseCareerOutcomeProcessor.deleteByCourseCareerOutcomeIds(userId, courseId, courseCareerOutcomeIds,
				linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course CareerOutcome deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
