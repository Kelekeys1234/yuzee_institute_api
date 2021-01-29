package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseContactPersonDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.endpoint.CourseContactPersonInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseContactPersonProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseContactPersonController implements CourseContactPersonInterface {

	@Autowired
	private CourseContactPersonProcessor courseContactPersonProcessor;

	
	@Override
	public ResponseEntity<?> saveAll(String userId, String courseId, @Valid ValidList<CourseContactPersonDto> courseContactPersonDtos)
			throws ValidationException, NotFoundException {
		log.info("inside CourseContactPersonController.saveAll");
		courseContactPersonProcessor.saveCourseContactPersons(userId, courseId, courseContactPersonDtos);
		return new GenericResponseHandlers.Builder().setMessage("Course Contact Persons added successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseAndUserIds(String userId, String courseId, List<String> userIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseContactPersonController.deleteByUserIds");
		courseContactPersonProcessor.deleteCourseContactPersonsByUserIds(userId, courseId, userIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Contact Persons deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
