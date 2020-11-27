package com.yuzee.app.controller.v1;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.dto.InstituteServiceDto;
import com.yuzee.app.endpoint.InstituteServiceInterface;
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
			List<InstituteServiceDto> instituteServiceDtos) throws Exception {
		log.info("Adding services for institute Id " + instituteId);
		if (ObjectUtils.isEmpty(instituteServiceDtos)) {
			log.error("InstiuteServiceDto list must not be null or empty");
			throw new ValidationException("InstiuteServiceDto list must not be null or empty");
		}
		boolean isInValidData = instituteServiceDtos.stream()
				.anyMatch(e -> ObjectUtils.isEmpty(e.getService()) || StringUtils.isEmpty(e.getService().getServiceId())
						&& StringUtils.isEmpty(e.getService().getServiceName()));
		if (isInValidData) {
			log.error("Atleast one of the service_id or service_name must not be empty");
			throw new ValidationException(
					"Atleast one of the service.service_id or service.service_name must not be empty");
		}
		instituteServiceProcessor.addInstituteService(userId, instituteId, instituteServiceDtos);
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
	public ResponseEntity<?> getInstituteServices(String instituteId) throws Exception {
		log.info("getting services for institute Id " + instituteId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setData(instituteServiceProcessor.getInstituteServices(instituteId))
				.setMessage("Institute services fetched successfully").create();
	}

}
