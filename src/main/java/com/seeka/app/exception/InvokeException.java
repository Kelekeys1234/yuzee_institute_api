package com.seeka.app.exception;

public class InvokeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvokeException(String message) {
		super(message);
	}

	public InvokeException(Throwable cause) {
		super(cause);
	}
}
