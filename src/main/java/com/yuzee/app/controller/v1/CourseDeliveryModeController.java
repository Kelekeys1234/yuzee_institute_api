package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseDeliveryModeRequestWrapper;
import com.yuzee.app.endpoint.CourseDeliveryModeInterface;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseDeliveryModesProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseDeliveryModeController implements CourseDeliveryModeInterface {

	@Autowired
	private CourseDeliveryModesProcessor courseDeliveryModeProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseDeliveryMode(String userId, String courseId,
			@Valid CourseDeliveryModeRequestWrapper request)
			throws ValidationException, NotFoundException, InternalServerException, CommonInvokeException {
		log.info("inside CourseDeliveryModeController.saveUpdateCourseDeliveryMode");
		courseDeliveryModeProcessor.saveUpdateCourseDeliveryModes(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage("Course DeliveryMode added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseDeliveryModeIds(String userId, String courseId,
			List<String> courseDeliveryModeIds, List<String> linkedCourseIds)
			throws ValidationException, NotFoundException {
		log.info("inside CourseDeliveryModeController.deleteByCourseDeliveryModeIds");
		courseDeliveryModeProcessor.deleteByCourseDeliveryModeIds(userId, courseId, courseDeliveryModeIds,
				linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course DeliveryMode deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
