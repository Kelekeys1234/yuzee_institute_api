package com.seeka.app.exception;

public class InternalServerException  extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(Throwable cause) {
		super(cause);
	} 
}
