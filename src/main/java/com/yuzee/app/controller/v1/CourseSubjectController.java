package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseSubjectRequestWrapper;
import com.yuzee.app.endpoint.CourseSubjectInterface;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseSubjectProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseSubjectController implements CourseSubjectInterface {

	@Autowired
	private CourseSubjectProcessor courseSubjectProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseSubjects(String userId, String courseId,
			@Valid CourseSubjectRequestWrapper request) throws ValidationException, NotFoundException {
		log.info("inside CourseSubjectController.saveUpdateCourseSubjects");
		courseSubjectProcessor.saveUpdateCourseSubjects(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage("Course Subject added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseSubjectIds(String userId, String courseId, List<String> courseSubjectIds,
			List<String> linkedCourseIds) throws ValidationException, NotFoundException {
		log.info("inside CourseSubjectController.deleteByCourseSubjectIds");
		courseSubjectProcessor.deleteByCourseSubjectIds(userId, courseId, courseSubjectIds, linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Subject deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
