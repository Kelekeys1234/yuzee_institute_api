package com.seeka.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.InstituteAdditionalInfoDto;
import com.seeka.app.endpoint.InstituteAdditionalInfoInterface;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.processor.InstituteAdditionalInfoProcessor;

@RestController
public class InstituteAdditionalInfoController implements InstituteAdditionalInfoInterface {

	@Autowired
	private InstituteAdditionalInfoProcessor instituteAdditionalInfoProcessor;
	
	@Override
	public ResponseEntity<?> addInstituteAdditionalInfo(String userId, String instituteId,
			@Valid InstituteAdditionalInfoDto instituteAdditionalInfoDto) throws Exception {
		instituteAdditionalInfoProcessor.addUpdateInstituteAdditionalInfo(userId, instituteId, instituteAdditionalInfoDto);
		return new GenericResponseHandlers.Builder().setMessage("Institute additional info added/updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteAdditionalInfo(String userId, String instituteId) throws Exception {
		InstituteAdditionalInfoDto instituteAdditionalInfoDto =  instituteAdditionalInfoProcessor.getInstituteAdditionalInfo(userId, instituteId, "PRIVATE");
		return new GenericResponseHandlers.Builder().setData(instituteAdditionalInfoDto).setMessage("Institute additional info fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstitutePublicAdditionalInfo(String instituteId) throws Exception {
		InstituteAdditionalInfoDto instituteAdditionalInfoDto =  instituteAdditionalInfoProcessor.getInstituteAdditionalInfo(null, instituteId, "PUBLIC");
		return new GenericResponseHandlers.Builder().setData(instituteAdditionalInfoDto).setMessage("Institute additional info fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

}
