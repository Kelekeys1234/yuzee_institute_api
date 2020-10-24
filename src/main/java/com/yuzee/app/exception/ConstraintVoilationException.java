package com.yuzee.app.exception;

import org.springframework.http.HttpStatus;

public class ConstraintVoilationException extends BaseRuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.BAD_REQUEST;

	/**
	 *
	 */
	public ConstraintVoilationException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public ConstraintVoilationException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public ConstraintVoilationException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public ConstraintVoilationException(final Throwable cause) {
		super(status, cause);
	}

}
