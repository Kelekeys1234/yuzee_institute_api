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
//import com.yuzee.app.dto.InstituteIdsRequestWrapperDto;
//import com.yuzee.app.dto.UnLinkInsituteDto;
//import com.yuzee.app.endpoint.CourseInstituteInterface;
////import com.yuzee.app.processor.CourseInstituteProcessor;
//import com.yuzee.common.lib.exception.NotFoundException;
//import com.yuzee.common.lib.exception.ValidationException;
//import com.yuzee.common.lib.handler.GenericResponseHandlers;
//import com.yuzee.local.config.MessageTranslator;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//public class CourseInstituteController implements CourseInstituteInterface {
//
//	@Autowired
//	CourseInstituteProcessor courseInstituteProcessor;
//	@Autowired
//	private MessageTranslator messageTranslator;
//	@Override
//	public ResponseEntity<?> createLinks(String userId, String courseId, @Valid InstituteIdsRequestWrapperDto request)
//			throws NotFoundException, ValidationException {
//		log.info("inside CourseInstituteController.createLinks");
//		courseInstituteProcessor.createLinks(userId, courseId, request.getInstituteIds());
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_institute.linked"))
//				.setStatus(HttpStatus.OK).create();
//	}
//
//	@Override
//	public ResponseEntity<?> getLinkedInstitutes(String userId, String courseId) throws Exception {
//		log.info("inside CourseInstituteController.getLinkedInstitutes");
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_institute.linked.retrieved"))
//				.setData(courseInstituteProcessor.getLinkedInstitutes(userId, courseId)).setStatus(HttpStatus.OK)
//				.create();
//	}
//
//	@Override
//	public ResponseEntity<?> unLinkInstitutes(String userId, String courseId, @Valid List<UnLinkInsituteDto> request) throws NotFoundException {
//		log.info("inside CourseInstituteController.unLinkInstitutes");
//		courseInstituteProcessor.unLinkInstitutes(userId, courseId, request);
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("course_institute.unlinked"))
//				.setStatus(HttpStatus.OK).create();
//	}
//}
