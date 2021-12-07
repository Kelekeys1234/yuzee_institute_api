package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseOtherRequirementDto;
import com.yuzee.app.endpoint.CourseOtherRequirementInterface;
import com.yuzee.app.processor.CourseOtherRequirementProcessor;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseOtherRequirementController implements CourseOtherRequirementInterface {

	@Autowired
	private CourseOtherRequirementProcessor courseOtherRequirementProcessor;

	@Override
	public ResponseEntity<?> saveOrUpdate(String userId, String courseId,
			@Valid CourseOtherRequirementDto courseOtherRequirementDto) throws ValidationException, NotFoundException {
		log.info("inside CourseOtherRequirementController.save");
		courseOtherRequirementProcessor.saveOrUpdateOtherRequirements(userId, courseId, courseOtherRequirementDto);
		return new GenericResponseHandlers.Builder().setMessage("Course Other requirement  successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getCourseOtherRequirements(String userId, String courseId)
			throws ValidationException, NotFoundException {
		log.info("inside CourseOtherRequirementController.save");
		return new GenericResponseHandlers.Builder().setMessage("Course Other requirement successfully.")
				.setData(courseOtherRequirementProcessor.getOtherRequirements(userId, courseId))
				.setStatus(HttpStatus.OK).create();
	}
}
