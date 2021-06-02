package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.CourseMinRequirementInterface;
import com.yuzee.app.processor.CourseMinRequirementProcessor;
import com.yuzee.common.lib.dto.institute.CourseMinRequirementDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseMinRequirementController implements CourseMinRequirementInterface {

	@Autowired
	private CourseMinRequirementProcessor courseMinRequirementProcessor;

	@Override
	public ResponseEntity<Object> saveCourseMinRequirement(String userId, String courseId,
			@Valid CourseMinRequirementDto courseMinRequirementDto)
			throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseMinRequirementController.saveCourseMinRequirement");
		return new GenericResponseHandlers.Builder()
				.setData(courseMinRequirementProcessor.saveCourseMinRequirement(userId, courseId,
						courseMinRequirementDto))
				.setMessage("Course Minimum Requirements saved successfully.").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> updateCourseMinRequirement(String userId, String courseId,
			@Valid CourseMinRequirementDto courseMinRequirementDto, String courseMinRequirementId)
			throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseMinRequirementController.updateCourseMinRequirement");
		return new GenericResponseHandlers.Builder()
				.setMessage("Course Minimum Requirements updated successfully.").setData(courseMinRequirementProcessor
						.updateCourseMinRequirement(userId, courseId, courseMinRequirementDto, courseMinRequirementId))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> getAllCourseMinimumRequirements(String userId, String courseId, Integer pageNumber,
			Integer pageSize) throws InternalServerException, NotFoundException, ForbiddenException {
		log.info("inside CourseMinRequirementController.getAllCourseMinimumRequirements");
		return new GenericResponseHandlers.Builder()
				.setMessage("Course Minimum Requirements fetched successfully.").setData(courseMinRequirementProcessor
						.getAllCourseMinimumRequirements(userId, courseId, pageNumber, pageSize))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<Object> deleteCourseMinRequirement(String userId, String courseId,
			String courseMinRequirementId, List<String> linkedCourseIds)
			throws InternalServerException, NotFoundException, ForbiddenException, ValidationException {
		log.info("inside CourseMinRequirementController.deleteCourseMinRequirement");
		courseMinRequirementProcessor.deleteCourseMinRequirement(userId, courseId, courseMinRequirementId, linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Minimum Requirement deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
