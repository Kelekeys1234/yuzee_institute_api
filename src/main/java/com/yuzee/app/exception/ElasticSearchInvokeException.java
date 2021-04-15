package com.yuzee.app.exception;

public class ElasticSearchInvokeException extends Exception {

	private static final long serialVersionUID = 1L;

	public ElasticSearchInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElasticSearchInvokeException(String message) {
		super(message);
	}

	public ElasticSearchInvokeException(Throwable cause) {
		super(cause);
	}
}
