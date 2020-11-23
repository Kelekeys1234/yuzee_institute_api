package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.InstituteAdditionalInfoDto;
import com.yuzee.app.endpoint.InstituteAdditionalInfoInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.InstituteAdditionalInfoProcessor;

@RestController
public class InstituteAdditionalInfoController implements InstituteAdditionalInfoInterface {

	@Autowired
	private InstituteAdditionalInfoProcessor instituteAdditionalInfoProcessor;

	@Override
	public ResponseEntity<?> addInstituteAdditionalInfo(String userId, String instituteId,
			@Valid InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws Exception {
		instituteAdditionalInfoProcessor.addUpdateInstituteAdditionalInfo(userId, instituteId,
				instituteAdditionalInfoDto);
		return new GenericResponseHandlers.Builder().setMessage("Institute additional info added/updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteAdditionalInfo(String instituteId) throws Exception {
		InstituteAdditionalInfoDto instituteAdditionalInfoDto = instituteAdditionalInfoProcessor
				.getInstituteAdditionalInfo(instituteId);
		return new GenericResponseHandlers.Builder().setData(instituteAdditionalInfoDto)
				.setMessage("Institute additional info fetched successfully").setStatus(HttpStatus.OK).create();
	}

}
