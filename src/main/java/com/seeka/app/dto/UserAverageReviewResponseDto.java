package com.seeka.app.dto;

import java.util.Map;

public class UserAverageReviewResponseDto {

	private Map<String, Double> data;
	private String message;
	private String status;

	public Map<String, Double> getData() {
		return data;
	}

	public void setData(Map<String, Double> data) {
		this.data = data;
	}

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
}
