package com.seeka.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.InstitutePageRequestDto;
import com.seeka.app.endpoint.InstitutePageRequestInterface;
import com.seeka.app.processor.InstitutePageRequestProcessor;

@RestController
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


}
