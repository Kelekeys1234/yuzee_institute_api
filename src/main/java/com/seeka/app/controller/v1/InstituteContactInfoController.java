package com.seeka.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.InstituteContactInfoDto;
import com.seeka.app.endpoint.InstituteContactInfoInterface;
import com.seeka.app.handler.GenericResponseHandlers;
import com.seeka.app.processor.InstituteContactInfoProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteContactInfoController implements InstituteContactInfoInterface {
	
	@Autowired
	private InstituteContactInfoProcessor instituteContactInfoProcessor;
	
	@Override
	public ResponseEntity<?> addUpdateInstituteContactInfo(String userId, String instituteId,
		@RequestBody @Valid InstituteContactInfoDto instituteContactInfoDto) throws Exception {
		instituteContactInfoProcessor.addUpdateInstituteContactInfo(userId, instituteId, instituteContactInfoDto);
		return new GenericResponseHandlers.Builder().setMessage("Institute contact info added/updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteContactInfo(String userId, String instituteId) throws Exception {
		InstituteContactInfoDto instituteContactInfoDto =instituteContactInfoProcessor.getInstituteContactInfo(userId, instituteId, "PRIVATE");
		return new GenericResponseHandlers.Builder().setData(instituteContactInfoDto).setMessage("Institute contact info added/updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	// sending null in as user id as dont want to resure same method
	@Override
	public ResponseEntity<?> getInstitutePublicContactInfo(String instituteId) throws Exception {
		InstituteContactInfoDto instituteContactInfoDto =instituteContactInfoProcessor.getInstituteContactInfo(null, instituteId, "PUBLIC");
		return new GenericResponseHandlers.Builder().setData(instituteContactInfoDto).setMessage("Institute contact info added/updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

}
