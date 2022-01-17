package com.yuzee.app.controller.v1;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.constant.InstituteJoinStatus;
import com.yuzee.app.dto.InstituteJoinRequestDto;
import com.yuzee.app.endpoint.InstituteJoinRequestInterface;
import com.yuzee.app.processor.InstituteJoinRequestProcessor;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteJoinRequestController implements InstituteJoinRequestInterface {

	@Autowired
	private InstituteJoinRequestProcessor instituteJoinRequestProcessor;
	
	@Autowired
	private MessageTranslator messageTranslator;
	
	@Override
	public ResponseEntity<?> requestJoinInstitute(String userId,
			@Valid InstituteJoinRequestDto instituteJoinRequestDto) throws Exception {
		InstituteJoinRequestDto instituteJoinRequestDtoResponse = instituteJoinRequestProcessor.instituteJoinRequest(userId, instituteJoinRequestDto);
		return new GenericResponseHandlers.Builder().setData(instituteJoinRequestDtoResponse).setMessage(messageTranslator.toLocale("institute_join_request.requested"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRequestsOfInstituteJoinRequest(String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteJoinStatus.class, status);
		if (!isStatusValid) {
			log.error(messageTranslator.toLocale("join-request.status.invalid" , Utils.getEnumNamesAsString(InstituteJoinStatus.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("join-request.status.invalid" , Utils.getEnumNamesAsString(InstituteJoinStatus.class)));
		}
		List<InstituteJoinRequestDto> listOfInstituteJoinRequestDto = instituteJoinRequestProcessor.getInstituteJoinRequestByStatus(status);
		return new GenericResponseHandlers.Builder().setData(listOfInstituteJoinRequestDto).setMessage(messageTranslator.toLocale("institute_join_request.request.list.retrieved"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstituetJoinRequestStatus(String instituteJoinRequestId, String status)
			throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(InstituteJoinStatus.class, status);
		if (!isStatusValid) {
			log.error(messageTranslator.toLocale("join-request.status.invalid" , Utils.getEnumNamesAsString(InstituteJoinStatus.class),Locale.US));
			throw new ValidationException(messageTranslator.toLocale("join-request.status.invalid" , Utils.getEnumNamesAsString(InstituteJoinStatus.class)));
		}
		instituteJoinRequestProcessor.updateInstituteJoinRequestStatus(instituteJoinRequestId, status);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("institute_join_request.updated"))
				.setStatus(HttpStatus.OK).create();
	}

}
