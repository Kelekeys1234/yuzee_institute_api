package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseEnglishEligibilityDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.endpoint.CourseEnglishEligibilityInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseEnglishEligibilityProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseEnglishEligibilityController implements CourseEnglishEligibilityInterface {

	@Autowired
	private CourseEnglishEligibilityProcessor courseEnglishEligibilityProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseEnglishEligibility(String userId, String courseId,
			@Valid ValidList<CourseEnglishEligibilityDto> courseEnglishEligibilityDtos)
			throws ValidationException, NotFoundException {
		log.info("inside CourseEnglishEligibilityController.saveUpdateCourseEnglishEligibility");
		courseEnglishEligibilityProcessor.saveUpdateCourseEnglishEligibilities(userId, courseId,
				courseEnglishEligibilityDtos);
		return new GenericResponseHandlers.Builder()
				.setMessage("Course EnglishEligibility added/ updated successfully.").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseEnglishEligibilityIds(String userId, String courseId,
			List<String> courseEnglishEligibilityIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseEnglishEligibilityController.deleteByCourseEnglishEligibilityIds");
		courseEnglishEligibilityProcessor.deleteByCourseEnglishEligibilityIds(userId, courseId,
				courseEnglishEligibilityIds);
		return new GenericResponseHandlers.Builder().setMessage("Course EnglishEligibility deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
