package com.yuzee.app.exception;

import org.springframework.http.HttpStatus;

public class UploaderException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092883758336411461L;
	
	private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	/**
	 *
	 */
	public UploaderException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public UploaderException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public UploaderException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public UploaderException(final Throwable cause) {
		super(status, cause);
	}

}
