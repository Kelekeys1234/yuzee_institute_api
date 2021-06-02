package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.app.endpoint.CourseFundingInterface;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.processor.CourseFundingProcessor;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CourseFundingController implements CourseFundingInterface {

	@Autowired
	private CourseFundingProcessor courseFundingProcessor;

	@Override
	public ResponseEntity<?> addFundingToAllInstituteCourses(String userId, String instituteId,
			CourseFundingDto courseFundingDto) throws ValidationException, NotFoundException, InvokeException {
		log.info("inside CourseFundingController.addFundingToAllInstituteCourses");
		courseFundingProcessor.addFundingToAllInstituteCourses(userId, instituteId,
				courseFundingDto.getFundingNameId());
		return new GenericResponseHandlers.Builder().setMessage("Funding added to all institute courses.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> saveAll(String userId, String courseId, @Valid CourseFundingRequestWrapper request)
			throws ValidationException, NotFoundException, InvokeException {
		log.info("inside CourseFundingController.saveAll");
		courseFundingProcessor.saveCourseFundings(userId, courseId, request);
		return new GenericResponseHandlers.Builder().setMessage("Course Fundings added successfully.")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> deleteByFundingNameIds(String userId, String courseId, List<String> fundingNameIds,
			List<String> linkedCourseIds) throws ValidationException, NotFoundException, ForbiddenException {
		log.info("inside CourseFundingController.deleteByFundingNameIds");
		courseFundingProcessor.deleteCourseFundingsByFundingNameIds(userId, courseId, fundingNameIds, linkedCourseIds);
		return new GenericResponseHandlers.Builder().setMessage("Course Fundings deleted successfully.")
				.setStatus(HttpStatus.OK).create();
	}
}
