package com.seeka.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.constant.InstituteJoinStatus;
import com.seeka.app.dto.InstituteJoinRequestDto;
import com.seeka.app.endpoint.InstituteJoinRequestInterface;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.processor.InstituteJoinRequestProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteJoinRequestController implements InstituteJoinRequestInterface {

	@Autowired
	private InstituteJoinRequestProcessor instituteJoinRequestProcessor;
	
	@Override
	public ResponseEntity<?> requestJoinInstitute(String userId,
			@Valid InstituteJoinRequestDto instituteJoinRequestDto) throws Exception {
		InstituteJoinRequestDto instituteJoinRequestDtoResponse = instituteJoinRequestProcessor.instituteJoinRequest(userId, instituteJoinRequestDto);
		return new GenericResponseHandlers.Builder().setData(instituteJoinRequestDtoResponse).setMessage("Institute join request successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRequestsOfInstituteJoinRequest(String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteJoinStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,GRANTED");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected  PENDING,REJECTED,GRANTED");
		}
		List<InstituteJoinRequestDto> listOfInstituteJoinRequestDto = instituteJoinRequestProcessor.getInstituteJoinRequestByStatus(status);
		return new GenericResponseHandlers.Builder().setData(listOfInstituteJoinRequestDto).setMessage("Institute join request list fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstituetJoinRequestStatus(String instituteJoinRequestId, String status)
			throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteJoinStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,GRANTED");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected  PENDING,REJECTED,GRANTED");
		}
		instituteJoinRequestProcessor.updateInstituteJoinRequestStatus(instituteJoinRequestId, status);
		return new GenericResponseHandlers.Builder().setMessage("Institute join request updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

}
