package com.seeka.app.dto;import java.math.BigInteger;

public class ErrorDto {
	
	private String message;
	private String code;
	 
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
