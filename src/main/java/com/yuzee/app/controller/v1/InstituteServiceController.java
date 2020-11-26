package com.yuzee.app.controller.v1;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.endpoint.InstituteServiceInterface;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.InstituteServiceProcessor;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class InstituteServiceController implements InstituteServiceInterface {

	@Autowired
	private InstituteServiceProcessor instituteServiceProcessor;

	@Override
	public ResponseEntity<?> addInstituteService(String userId, String instituteId,
			@RequestBody @Valid InstituteServiceDto instituteServiceDto) throws Exception {
		log.info("Adding services for institute Id " + instituteId);
		boolean isInValidData = instituteServiceDto.getServices().stream()
				.anyMatch(e -> StringUtils.isEmpty(e.getServiceId()) && StringUtils.isEmpty(e.getServiceName()));
		if (isInValidData) {
			throw new ValidationException("Atleast one of the service_id or service_name must not be empty");
		}
		instituteServiceProcessor.addInstituteService(userId, instituteId, instituteServiceDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Institute services added successfully").create();
	}

	@Override
	public ResponseEntity<?> deleteInstituteServiceByServiceIds(String instituteId, List<String> serviceIds)
			throws Exception {
		log.info("deleting services for institute Id " + instituteId);
		instituteServiceProcessor.deleteInstituteService(instituteId, serviceIds);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Institute services deleted successfully").create();
	}

	@Override
	public ResponseEntity<?> getInstituteService(String instituteId) throws Exception {
		log.info("getting services for institute Id " + instituteId);
		InstituteServiceDto instituteServiceDto = instituteServiceProcessor.getServiceByInstituteId(instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(instituteServiceDto)
				.setMessage("Institute services fetched successfully").create();
	}

}
