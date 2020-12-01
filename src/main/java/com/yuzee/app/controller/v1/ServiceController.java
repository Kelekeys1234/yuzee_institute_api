package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.ServiceInterface;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.ServiceProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("serviceControllerV1")
public class ServiceController implements ServiceInterface {

	@Autowired
	private ServiceProcessor serviceProcessor;

	@Override
	public ResponseEntity<?> getAllServices(final Integer pageNumber, final Integer pageSize)
			throws ValidationException, NotFoundException, InvokeException {
		log.debug("inside getAllServices(final Integer pageNumber, final Integer pageSize) method");
		if (pageNumber < 1) {
			log.error("Page number can not be less than 1");
			throw new ValidationException("Page number can not be less than 1");
		}

		if (pageSize < 1) {
			log.error("Page size can not be less than 1");
			throw new ValidationException("Page size can not be less than 1");
		}
		return new GenericResponseHandlers.Builder().setData(serviceProcessor.getAllServices(pageNumber, pageSize))
				.setMessage("Services fetched successfully").setStatus(HttpStatus.OK).create();
	}
}
