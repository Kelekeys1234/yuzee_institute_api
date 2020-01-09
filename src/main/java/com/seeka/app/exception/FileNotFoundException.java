package com.seeka.app.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author SeekADegree
 *
 */
public class FileNotFoundException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.NOT_FOUND;

	/**
	 *
	 */
	public FileNotFoundException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public FileNotFoundException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public FileNotFoundException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public FileNotFoundException(final Throwable cause) {
		super(status, cause);
	}

}
