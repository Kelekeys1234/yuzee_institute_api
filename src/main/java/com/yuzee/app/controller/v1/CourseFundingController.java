package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.CourseFundingDto;
import com.yuzee.app.endpoint.CourseFundingInterface;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CourseFundingProcessor;

@RestController
public class CourseFundingController implements CourseFundingInterface {

	@Autowired
	private CourseFundingProcessor courseFundingProcessor;

	@Override
	public ResponseEntity<?> addFundingToAllInstituteCourses(String userId, String instituteId,
			CourseFundingDto courseFundingDto) throws ValidationException, NotFoundException {
		courseFundingProcessor.addFundingToAllInstituteCourses(userId, instituteId,
				courseFundingDto.getFundingNameId());
		return new GenericResponseHandlers.Builder().setMessage("Funding added to all institute courses.")
				.setStatus(HttpStatus.OK).create();
	}
}
