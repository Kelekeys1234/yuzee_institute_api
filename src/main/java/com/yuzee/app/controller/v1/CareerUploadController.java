package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.endpoint.CareerUploadInterface;
import com.yuzee.app.processor.CareerUploadProcessor;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.local.config.MessageTranslator;

@RestController
public class CareerUploadController implements CareerUploadInterface {

	@Autowired
	private CareerUploadProcessor careersProcessor;
	
	@Autowired
	private MessageTranslator messageTranslator;
	
	@Override
	public ResponseEntity<?> uploadCareer(MultipartFile multipartFile) {
		careersProcessor.uploadCareerJobs(multipartFile);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(messageTranslator.toLocale("career_data.uploded")).create();
	}
}
