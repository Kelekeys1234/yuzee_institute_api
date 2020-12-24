package com.yuzee.app.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.FORBIDDEN;

	/**
	 *
	 */
	public ForbiddenException() {
		super(status, "user has no access for the requested operation");
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public ForbiddenException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public ForbiddenException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public ForbiddenException(final Throwable cause) {
		super(status, cause);
	}

}
