package com.seeka.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.constant.RecommendRequestStatus;
import com.seeka.app.dto.InstituteRecommendPageRequestDto;
import com.seeka.app.endpoint.InstituteRecommedPageRequestInterface;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.processor.InstituteRecommendPageRequestProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteRecommendPageRequestController implements InstituteRecommedPageRequestInterface {
	
	@Autowired
	private InstituteRecommendPageRequestProcessor instituteRecommendPageRequestProcessor;
	
	@Override
	public ResponseEntity<?> recommendInstitutePage(String userId,
			@Valid InstituteRecommendPageRequestDto instituteRecommendPageRequestDto) throws Exception {
		instituteRecommendPageRequestProcessor.recommendInstiutePageRequest(userId, instituteRecommendPageRequestDto);
		return new GenericResponseHandlers.Builder().setMessage("recommendation institute request successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getRecommendationOfInstitutePage(String status) throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(RecommendRequestStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,PROCESSED");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected PENDING,PROCESSED");
		}
		List<InstituteRecommendPageRequestDto> listOfRecommendRequestDto = instituteRecommendPageRequestProcessor.getInstituteRecommendRequestByStatus(status);
		return new GenericResponseHandlers.Builder().setData(listOfRecommendRequestDto).setMessage("Institute recommend request list fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> updateInstitueRecommendationStatus(String instituteRecommendationRequestId, String status)
			throws Exception {
		boolean isStatusValid = EnumUtils.isValidEnum(RecommendRequestStatus.class, status);
		if (!isStatusValid) {
			log.error("status value is invalid in request passed " + status
					+ " expected PENDING,PROCESSED");
			throw new ValidationException("status value is invalid in request passed " + status
					+ " expected PENDING,PROCESSED");
		}
		if (status.equalsIgnoreCase(RecommendRequestStatus.PENDING.toString())) {
			log.error("Can only change status from Pending to Processed");
			throw new ValidationException("Can only change status from Pending to Processed");
		}
		instituteRecommendPageRequestProcessor.updateInstituteRecommedRequestStatus(instituteRecommendationRequestId, status);
		return new GenericResponseHandlers.Builder().setMessage("recommendation institute request updated successfully")
				.setStatus(HttpStatus.OK).create();
	}


}
