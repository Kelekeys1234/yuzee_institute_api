package com.seeka.app.dto;

import java.util.List;

public class UserReviewResponseDto {

	private List<UserReviewResultDto> data;
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

	public List<UserReviewResultDto> getData() {
		return data;
	}

	public void setData(List<UserReviewResultDto> data) {
		this.data = data;
	}

	
	
}
