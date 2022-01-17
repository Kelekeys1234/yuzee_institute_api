package com.yuzee.app.controller.v1;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.endpoint.ServiceInterface;
import com.yuzee.app.processor.ServiceProcessor;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.GenericResponseHandlers;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("serviceControllerV1")
public class ServiceController implements ServiceInterface {
	@Autowired
	private MessageTranslator messageTranslator;
	
	@Autowired
	private ServiceProcessor serviceProcessor;

	public ResponseEntity<?> getAllServices(final Integer pageNumber, final Integer pageSize)
			throws ValidationException, NotFoundException, InvokeException {
		log.debug("inside getAllServices(final Integer pageNumber, final Integer pageSize) method");
		if (pageNumber < 1) {
			log.error(messageTranslator.toLocale("page_number.not_zero",Locale.US));
			throw new ValidationException(messageTranslator.toLocale("page_number.not_zero"));
		}

		if (pageSize < 1) {
			log.error(messageTranslator.toLocale("page_size.not_zero",Locale.US));
			throw new ValidationException(messageTranslator.toLocale("page_size.not_zero"));
		}
		return new GenericResponseHandlers.Builder().setData(serviceProcessor.getAllServices(pageNumber, pageSize))
				.setMessage(messageTranslator.toLocale("services.list.retrieved")).setStatus(HttpStatus.OK).create();
	}
}
