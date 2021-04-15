package com.yuzee.app.exception;

import org.springframework.http.HttpStatus;

public class IOException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.NOT_FOUND;

	/**
	 *
	 */
	public IOException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public IOException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public IOException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public IOException(final Throwable cause) {
		super(status, cause);
	}

}
