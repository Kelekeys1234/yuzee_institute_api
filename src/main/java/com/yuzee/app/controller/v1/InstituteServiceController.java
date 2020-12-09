package com.yuzee.app.controller.v1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.endpoint.InstituteServiceInterface;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.InstituteServiceProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class InstituteServiceController implements InstituteServiceInterface {

	@Autowired
	private InstituteServiceProcessor instituteServiceProcessor;

	@Override
	public ResponseEntity<?> addInstituteService(String userId, String instituteId,
			InstituteServiceDto instituteServiceDto) throws Exception {
		log.info("Adding services for institute Id " + instituteId);
		if (ObjectUtils.isEmpty(instituteServiceDto.getService())
				|| StringUtils.isEmpty(instituteServiceDto.getService().getServiceId())
						&& StringUtils.isEmpty(instituteServiceDto.getService().getServiceName())) {
			log.error("Atleast one of the service_id or service_name must not be empty");
			throw new ValidationException(
					"Atleast one of the service.service_id or service.service_name must not be empty");
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setData(instituteServiceProcessor.addInstituteService(userId, instituteId, instituteServiceDto))
				.setMessage("Institute services added successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteById(String instituteServiceId) throws NotFoundException, InvokeException {
		log.info("deleting instituteService Id: {}" + instituteServiceId);
		instituteServiceProcessor.deleteInstituteService(instituteServiceId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Institute services deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstituteServices(String instituteId) {
		log.info("getting services for institute Id " + instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setData(instituteServiceProcessor.getInstituteServices(instituteId))
				.setMessage("Institute services fetched successfully").create();
	}

}
