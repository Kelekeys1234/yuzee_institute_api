package com.seeka.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.constant.PageRequestStatus;
import com.seeka.app.dto.InstitutePageRequestDto;
import com.seeka.app.endpoint.InstitutePageRequestInterface;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.processor.InstitutePageRequestProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstitutePageRequestController implements InstitutePageRequestInterface {

	@Autowired
	private InstitutePageRequestProcessor institutePageRequestProcessor;
	
	@Override
	public ResponseEntity<?> requestInstitutePageAccess(String userId, String instituteId,
		 @RequestBody	@Valid InstitutePageRequestDto institutePageRequestDto) throws Exception {
		InstitutePageRequestDto institutePageRequestDtoResponse = institutePageRequestProcessor.requestInstiutePageRequest(userId, instituteId, institutePageRequestDto);
		return new GenericResponseHandlers.Builder().setData(institutePageRequestDtoResponse).setMessage("Institute page request successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRequestsOfInstitutePageAccess(String instituteId, String status) throws Exception {

		boolean isStatusValid = EnumUtils.isValidEnum(PageRequestStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
		}
		List<InstitutePageRequestDto> listOfInstitutePageRequestDto = institutePageRequestProcessor.getInstitutePageRequestByInstituteIdAndStatus(instituteId, status);
		return new GenericResponseHandlers.Builder().setData(listOfInstitutePageRequestDto).setMessage("Institute page request list fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstituetPageRequestStatus(String institutePageRequestId, String status)
			throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(PageRequestStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected PENDING,REJECTED,REVOKED,ACTIVE");
		}
		institutePageRequestProcessor.updateInstitutePageRequestStatus(institutePageRequestId, status);
		return new GenericResponseHandlers.Builder().setMessage("Institute page request updated successfully")
				.setStatus(HttpStatus.OK).create();
	}
}
