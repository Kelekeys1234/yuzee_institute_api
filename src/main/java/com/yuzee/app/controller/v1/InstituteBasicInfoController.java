package com.yuzee.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.InstituteBasicInfoDto;
import com.yuzee.app.endpoint.InstituteBasicInfoInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.InstituteBasicInfoProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteBasicInfoController implements InstituteBasicInfoInterface {
	
	@Autowired
	private InstituteBasicInfoProcessor instituteBasicInfoProcessor;
	
	
	@Override
	public ResponseEntity<?> addUpdateInstituteBasicInfo(String userId, String instituteId,
		@RequestBody @Valid InstituteBasicInfoDto instituteBasicInfoDto) throws Exception {
		instituteBasicInfoProcessor.addUpdateInstituteBasicInfo(userId, instituteId, instituteBasicInfoDto);
		return new GenericResponseHandlers.Builder().setMessage("Institute basic info added/updated successfully")
				.setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getInstituteBasicInfo(String userId, String instituteId) throws Exception {
		InstituteBasicInfoDto instituteBasicInfoDto = instituteBasicInfoProcessor.getInstituteBasicInfo(userId, instituteId, "PRIVATE", true);
		return new GenericResponseHandlers.Builder().setData(instituteBasicInfoDto).setMessage("Institute basic info fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

	// passing null user id as dont want to duplicate same set of code 
	@Override
	public ResponseEntity<?> getInstitutePublicBasicInfo(String instituteId, boolean includeInstituteLogo) throws Exception {
		InstituteBasicInfoDto instituteBasicInfoDto = instituteBasicInfoProcessor.getInstituteBasicInfo(null, instituteId, "PUBLIC",includeInstituteLogo);
		return new GenericResponseHandlers.Builder().setData(instituteBasicInfoDto).setMessage("Institute basic info fetched successfully")
				.setStatus(HttpStatus.OK).create();
	}

}
