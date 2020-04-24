package com.seeka.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.InstituteServiceDto;
import com.seeka.app.endpoint.InstituteServiceInterface;
import com.seeka.app.processor.InstituteServiceProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteServiceController implements InstituteServiceInterface {
	
	@Autowired
	private InstituteServiceProcessor instituteServiceProcessor;
	
	@Override
	public ResponseEntity<?> addInstituteService(String userId, String instituteId,
		 @RequestBody  @Valid InstituteServiceDto instituteServiceDto) throws Exception {
		log.info("Adding services for institute Id " +instituteId);
		instituteServiceProcessor.addInstituteService(userId, instituteId, instituteServiceDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Institute services added successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteInstituteServiceById(String userId, String instituteId,
			List<String> instituteServiceId) throws Exception {
		log.info("deleting services for institute Id " +instituteId);
		instituteServiceProcessor.deleteInstituteService(userId, instituteId, instituteServiceId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Institute services deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstituteService(String userId, String instituteId) throws Exception {
		log.info("getting services for institute Id " +instituteId);
		InstituteServiceDto instituteServiceDto = instituteServiceProcessor.getServiceByInstituteId(userId, instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteServiceDto).setMessage("Institute services fetched successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstitutePublicService(String instituteId) throws Exception {
		log.info("getting services for institute Id " +instituteId);
		InstituteServiceDto instituteServiceDto = instituteServiceProcessor.getPublicServiceByInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteServiceDto).setMessage("Institute public services fetched successfully").create();
	}
}
