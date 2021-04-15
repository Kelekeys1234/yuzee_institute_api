package com.yuzee.app.exception;

public class CommonInvokeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CommonInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonInvokeException(String message) {
		super(message);
	}

	public CommonInvokeException(Throwable cause) {
		super(cause);
	}
}
