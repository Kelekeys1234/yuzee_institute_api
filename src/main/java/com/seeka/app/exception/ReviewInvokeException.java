package com.seeka.app.exception;

public class ReviewInvokeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ReviewInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviewInvokeException(String message) {
		super(message);
	}

	public ReviewInvokeException(Throwable cause) {
		super(cause);
	}
}
