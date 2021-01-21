package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseSubjectDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.app.endpoint.CourseSubjectInterface;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseSubjectProcessor;

@RestController
public class CourseSubjectController implements CourseSubjectInterface {

	@Autowired
	private CourseSubjectProcessor courseSubjectProcessor;

	@Override
	public ResponseEntity<?> saveUpdateCourseSubjects(String userId, String courseId,
			@Valid ValidList<CourseSubjectDto> courseSubjectDtos) throws ValidationException, NotFoundException {
		courseSubjectProcessor.saveUpdateCourseSubjects(userId, courseId, courseSubjectDtos);
		return new GenericResponseHandlers.Builder().setMessage("Course Subject added/ updated successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByCourseSubjectIds(String userId, String courseId, List<String> courseSubjectIds)
			throws ValidationException, NotFoundException, ForbiddenException {
		courseSubjectProcessor.deleteByCourseSubjectIds(userId, courseId, courseSubjectIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Subject deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
