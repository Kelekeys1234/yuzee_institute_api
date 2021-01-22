package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseCareerOutcomeDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.endpoint.CourseCareerOutcomeInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseCareerOutcomeProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseCareerOutcomeController implements CourseCareerOutcomeInterface {

	@Autowired
	private CourseCareerOutcomeProcessor courseCareerOutcomeProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseCareerOutcomes(String userId, String courseId,
			@Valid ValidList<CourseCareerOutcomeDto> courseCareerOutcomeDtos)
			throws ValidationException, NotFoundException {
		log.info("inside CourseCareerOutcomeController.saveUpdateCourseCareerOutcomes");
		courseCareerOutcomeProcessor.saveUpdateCourseCareerOutcomes(userId, courseId, courseCareerOutcomeDtos);
		return new GenericResponseHandlers.Builder().setMessage("Course CareerOutcomes added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseCareerOutcomeIds(String userId, String courseId,
			List<String> courseCareerOutcomeIds) throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseCareerOutcomeController.deleteByCourseCareerOutcomeIds");
		courseCareerOutcomeProcessor.deleteByCourseCareerOutcomeIds(userId, courseId, courseCareerOutcomeIds);
		return new GenericResponseHandlers.Builder().setMessage("Course CareerOutcome deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
