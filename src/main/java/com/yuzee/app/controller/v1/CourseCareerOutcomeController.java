package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseCareerOutcomeDto;
import com.yuzee.app.endpoint.CourseCareerOutcomeInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseCareerOutcomeProcessor;

@RestController
public class CourseCareerOutcomeController implements CourseCareerOutcomeInterface {

	@Autowired
	private CourseCareerOutcomeProcessor courseCareerOutcomeProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseCareerOutcomes(String userId, String courseId,
			@Valid List<CourseCareerOutcomeDto> courseCareerOutcomeDtos) throws ValidationException, NotFoundException {
		courseCareerOutcomeProcessor.saveUpdateCourseCareerOutcomes(userId, courseId, courseCareerOutcomeDtos);
		return new GenericResponseHandlers.Builder().setMessage("Course CareerOutcomes added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseCareerOutcomeIds(String userId, List<String> courseCareerOutcomeIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		courseCareerOutcomeProcessor.deleteByCourseCareerOutcomeIds(userId, courseCareerOutcomeIds);
		return new GenericResponseHandlers.Builder().setMessage("Course CareerOutcome deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
