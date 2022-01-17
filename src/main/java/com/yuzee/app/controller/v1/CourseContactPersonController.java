package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseContactPersonRequestWrapper;
import com.yuzee.app.endpoint.CourseContactPersonInterface;
import com.yuzee.app.processor.CourseContactPersonProcessor;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseContactPersonController implements CourseContactPersonInterface {

	@Autowired
	private CourseContactPersonProcessor courseContactPersonProcessor;
	@Autowired
	private MessageTranslator messageTranslator;
	@Override
	public ResponseEntity<?> saveAll(String userId, String courseId, @Valid CourseContactPersonRequestWrapper request)
			throws ValidationException, NotFoundException, InvokeException {

		log.info("inside CourseContactPersonController.saveAll");

		courseContactPersonProcessor.saveCourseContactPersons(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_contact.added"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseAndUserIds(String userId, String courseId, List<String> userIds,
			List<String> linkedCourseIds) throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseContactPersonController.deleteByUserIds");
		courseContactPersonProcessor.deleteCourseContactPersonsByUserIds(userId, courseId, userIds, linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_contact.deleted"))
				.setStatus(HttpStatus.OK).create();
	}
}
