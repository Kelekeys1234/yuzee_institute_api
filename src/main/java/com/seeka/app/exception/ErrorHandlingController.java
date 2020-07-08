package com.seeka.app.exception;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seeka.app.handler.GenericResponseHandlers;

/**
 *
 * @author SeekADegree
 *
 */
@ControllerAdvice(basePackages = "com.seeka.app")
public class ErrorHandlingController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Central exception handler and generate common custom response
	 *
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ResponseEntity<Object> handleControllerException(final HttpServletRequest request, final Throwable exception) {
		HttpStatus status = null;
		String message = null;
		if(exception instanceof ParseException) {
			status = ((BaseException) exception).getStatus();
			message = exception.getMessage();
		} else if (exception instanceof BaseException) {
			status = ((BaseException) exception).getStatus();
			message = exception.getMessage();
		} else if (exception instanceof BaseRuntimeException) {
			status = ((BaseRuntimeException) exception).getStatus();
			message = exception.getMessage();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = exception.getMessage();
		}
		StringBuffer requestedURL = request.getRequestURL();
		logger.info("Requested URL:{}", requestedURL);
		logger.error("exception : {}", exception);
		return new GenericResponseHandlers.Builder().setStatus(status).setMessage(message).create();
	}

}
