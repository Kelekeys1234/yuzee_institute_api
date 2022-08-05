//package com.yuzee.app.controller.v1;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yuzee.app.endpoint.CourseScholarshipInterface;
//import com.yuzee.app.processor.CourseScholarshipProcessor;
//import com.yuzee.common.lib.dto.institute.CourseScholarshipDto;
//import com.yuzee.common.lib.exception.ForbiddenException;
//import com.yuzee.common.lib.exception.InternalServerException;
//import com.yuzee.common.lib.exception.NotFoundException;
//import com.yuzee.common.lib.exception.ValidationException;
//import com.yuzee.common.lib.handler.GenericResponseHandlers;
//import com.yuzee.local.config.MessageTranslator;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//public class CourseScholarshipController implements CourseScholarshipInterface {
//
//	@Autowired
//	private CourseScholarshipProcessor courseScholarshipProcessor;
//	@Autowired
//	private MessageTranslator messageTranslator;
//	@Override
//	public ResponseEntity<?> saveUpdateCourseScholarship(String userId, String courseId,
//			@Valid CourseScholarshipDto courseScholarshipDto)
//			throws ValidationException, NotFoundException, ForbiddenException {
//		log.info("inside CourseScholarshipController.saveUpdateCourseScholarship");
//		courseScholarshipProcessor.saveUpdateCourseScholarships(userId, courseId, courseScholarshipDto);
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_scholarship.added"))
//				.setStatus(HttpStatus.OK).create();
//	}
//
//	@Override
//	public ResponseEntity<?> deleteAllCourseScholarship(String userId, String courseId, List<String> linkedCourseIds)
//			throws InternalServerException, NotFoundException, ForbiddenException {
//		log.info("inside CourseScholarshipController.deleteAllCourseScholarship");
//		courseScholarshipProcessor.deleteCourseScholarship(userId, courseId, linkedCourseIds);
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_scholarship.deleted"))
//				.setStatus(HttpStatus.OK).create();
//	}
//
//	@Override
//	public ResponseEntity<?> getCourseScholarships(String courseId) throws ValidationException, NotFoundException {
//		log.info("inside CourseScholarshipController.getCourseScholarships");
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_scholarship.retrieved"))
//				.setData(courseScholarshipProcessor.getCourseScholarship(courseId))
//				.setStatus(HttpStatus.OK).create();
//	}
//}
