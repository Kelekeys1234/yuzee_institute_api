package com.seeka.app.dto;

import java.util.Map;

public class UserReviewResponseDto {

	private Map<String,Object> data;
	private String message;
	private String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

	
	
}
