package com.yuzee.app.controller.v1;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.constant.RecommendRequestStatus;
import com.yuzee.app.dto.InstituteRecommendPageRequestDto;
import com.yuzee.app.endpoint.InstituteRecommedPageRequestInterface;
import com.yuzee.app.processor.InstituteRecommendPageRequestProcessor;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteRecommendPageRequestController implements InstituteRecommedPageRequestInterface {
	
	@Autowired
	private InstituteRecommendPageRequestProcessor instituteRecommendPageRequestProcessor;
	
	@Autowired
	private MessageTranslator messageTranslator;
	@Override
	public ResponseEntity<?> recommendInstitutePage(String userId,
			@Valid InstituteRecommendPageRequestDto instituteRecommendPageRequestDto) throws Exception {
		instituteRecommendPageRequestProcessor.recommendInstiutePageRequest(userId, instituteRecommendPageRequestDto);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("institute_recommend.requested"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRecommendationOfInstitutePage(String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(RecommendRequestStatus.class, status);
		if (!isStatusValid) {
			log.error(messageTranslator.toLocale("recommend-page_request.status.invalid", Utils.getEnumNamesAsString(RecommendRequestStatus.class) ,Locale.US));
			throw new ValidationException(messageTranslator.toLocale("recommend-page_request.status.invalid", Utils.getEnumNamesAsString(RecommendRequestStatus.class)));
		}
		List<InstituteRecommendPageRequestDto> listOfRecommendRequestDto = instituteRecommendPageRequestProcessor.getInstituteRecommendRequestByStatus(status);
		return new GenericResponseHandlers.Builder().setData(listOfRecommendRequestDto).setMessage(messageTranslator.toLocale("institute_recommend.request.list.retrieved"))
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstitueRecommendationStatus(String instituteRecommendationRequestId, String status)
			throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(RecommendRequestStatus.class, status);
		if (!isStatusValid) {
			log.error(messageTranslator.toLocale("recommend-page_request.status.invalid", Utils.getEnumNamesAsString(RecommendRequestStatus.class) ,Locale.US));
			throw new ValidationException(messageTranslator.toLocale("recommend-page_request.status.invalid", Utils.getEnumNamesAsString(RecommendRequestStatus.class)));
		}
		if (status.equalsIgnoreCase(RecommendRequestStatus.PENDING.toString())) {
			log.error(messageTranslator.toLocale("recommend-page_request.status.changed",Locale.US));
			throw new ValidationException(messageTranslator.toLocale("recommend-page_request.status.changed"));
		}
		instituteRecommendPageRequestProcessor.updateInstituteRecommedRequestStatus(instituteRecommendationRequestId, status);
		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("institute_recommend.updated"))
				.setStatus(HttpStatus.OK).create();
	}


}
