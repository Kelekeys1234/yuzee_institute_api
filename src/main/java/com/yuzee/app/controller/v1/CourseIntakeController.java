package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseIntakeDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.endpoint.CourseIntakeInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseIntakeProcessor;

@RestController
public class CourseIntakeController implements CourseIntakeInterface {

	@Autowired
	private CourseIntakeProcessor courseIntakeProcessor;

	@Override
	public ResponseEntity<?> saveAll(String userId, String courseId, @Valid ValidList<CourseIntakeDto> courseIntakeDtos)
			throws ValidationException, NotFoundException {
		courseIntakeProcessor.saveCourseIntakes(userId, courseId, courseIntakeDtos);
		return new GenericResponseHandlers.Builder().setMessage("Course Intakes added successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseIntakeIds(String userId, String courseId, List<String> intakeIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		courseIntakeProcessor.deleteByCourseIntakeIds(userId, courseId, intakeIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Intakes deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
