package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CoursePaymentDto;
import com.yuzee.app.endpoint.CoursePaymentInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CoursePaymentProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CoursePaymentController implements CoursePaymentInterface {

	@Autowired
	private CoursePaymentProcessor coursePaymentProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCoursePayment(String userId, String courseId,
			@Valid CoursePaymentDto coursePaymentDto)
			throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CoursePaymentController.saveUpdateCoursePayment method");
		coursePaymentProcessor.saveUpdateCoursePayment(userId, courseId, coursePaymentDto);
		return new GenericResponseHandlers.Builder().setMessage("Course Payment saved/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteCoursePayment(String userId, String courseId, List<String> linkedCourseIds)
			throws InternalServerException, NotFoundException, ForbiddenException {
		log.info("inside CoursePaymentController.deleteCoursePayment method");
		coursePaymentProcessor.deleteCoursePayment(userId, courseId);
		return new GenericResponseHandlers.Builder().setMessage("Course Payment deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
