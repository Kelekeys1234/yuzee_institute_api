package com.yuzee.app.exception;

import org.springframework.http.HttpStatus;

public class RuntimeValidationException extends BaseRuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.BAD_REQUEST;

	/**
	 *
	 */
	public RuntimeValidationException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public RuntimeValidationException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public RuntimeValidationException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public RuntimeValidationException(final Throwable cause) {
		super(status, cause);
	}

}
