package com.yuzee.app.dto;

import lombok.Data;

@Data
public class FollowerCountResponseDto {
	private FollowerCountDto data;

	private String message;

	private String status;
}
