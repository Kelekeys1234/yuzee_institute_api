package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseDeliveryModesDto;
import com.yuzee.app.endpoint.CourseDeliveryModeInterface;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseDeliveryModesProcessor;

@RestController
public class CourseDeliveryModeController implements CourseDeliveryModeInterface {

	@Autowired
	private CourseDeliveryModesProcessor courseDeliveryModeProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseDeliveryMode(String userId, String courseId,
			@Valid List<CourseDeliveryModesDto> courseDeliveryModeDtos)
			throws ValidationException, NotFoundException, InternalServerException, CommonInvokeException {
		courseDeliveryModeProcessor.saveUpdateCourseDeliveryModes(userId, courseId, courseDeliveryModeDtos);
		return new GenericResponseHandlers.Builder().setMessage("Course DeliveryMode added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseDeliveryModeIds(String userId, List<String> courseDeliveryModeIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		courseDeliveryModeProcessor.deleteByCourseDeliveryModeIds(userId, courseDeliveryModeIds);
		return new GenericResponseHandlers.Builder().setMessage("Course DeliveryMode deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
