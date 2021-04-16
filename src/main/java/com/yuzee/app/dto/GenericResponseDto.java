package com.yuzee.app.dto;

import lombok.Data;

@Data
public class GenericResponseDto<T> {
	private String message;
	private T data;
	private String status;
}
