package com.yuzee.app.exception;

import org.springframework.http.HttpStatus;

public class RuntimeNotFoundException extends BaseRuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.NOT_FOUND;

	/**
	 *
	 */
	public RuntimeNotFoundException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public RuntimeNotFoundException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public RuntimeNotFoundException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public RuntimeNotFoundException(final Throwable cause) {
		super(status, cause);
	}

}
