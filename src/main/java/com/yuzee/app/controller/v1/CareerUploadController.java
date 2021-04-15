package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.endpoint.CareerUploadInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.CareerUploadProcessor;

@RestController
public class CareerUploadController implements CareerUploadInterface {

	@Autowired
	private CareerUploadProcessor careersProcessor;
	
	@Override
	public ResponseEntity<?> uploadCareer(MultipartFile multipartFile) {
		careersProcessor.uploadCareerJobs(multipartFile);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Career Data uploaded successfully")
				.create();
	}
}
