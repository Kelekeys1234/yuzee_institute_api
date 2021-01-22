package com.yuzee.app.exception;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuzee.app.handler.GenericResponseHandlers;

@ControllerAdvice(basePackages = "com.yuzee.app")
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
		if (exception instanceof ParseException) {
			status = ((BaseException) exception).getStatus();
			message = exception.getMessage();
		} else if (exception instanceof ForbiddenException) {
			status = ((ForbiddenException) exception).getStatus();
			message = exception.getMessage();
		} else if (exception instanceof ValidationException) {
			status = HttpStatus.BAD_REQUEST;
			message = exception.getMessage();
		} else if (exception instanceof MethodArgumentNotValidException) {
			status = HttpStatus.BAD_REQUEST;
			List<String> errors = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()
					.stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
			message = errors.toString();
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
