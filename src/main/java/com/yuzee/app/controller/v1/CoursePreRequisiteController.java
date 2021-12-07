package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CoursePreRequisiteRequestWrapper;
import com.yuzee.app.endpoint.CoursePreRequisiteInterface;
import com.yuzee.app.processor.CoursePrerequisiteProcessor;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CoursePreRequisiteController implements CoursePreRequisiteInterface {

	@Autowired
	private CoursePrerequisiteProcessor coursePreRequisiteProcessor;

	@Override
	public ResponseEntity<?> saveUpdatePreRequisites(String userId, String courseId,
			@Valid CoursePreRequisiteRequestWrapper request)
			throws ValidationException, NotFoundException, InternalServerException {
		log.info("inside CoursePreRequisiteController.saveUpdatePreRequisites");
		coursePreRequisiteProcessor.saveUpdatePreRequisites(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage("Course PreRequisites added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByPreRequisiteIds(String userId, String courseId,
			List<String> courseDeliveryModeIds, List<String> linkedCourseIds)
			throws ValidationException, NotFoundException {
		log.info("inside CoursePreRequisiteController.deleteByPreRequisiteIds");
		coursePreRequisiteProcessor.deleteByPreRequisiteIds(userId, courseId, courseDeliveryModeIds,
				linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course PreRequisites deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
