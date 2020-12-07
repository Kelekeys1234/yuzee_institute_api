package com.yuzee.app.dto;

import lombok.Data;

@Data
public class GenericWrapperDto<T> {
	private String message;
	private String status;
	private T data;
}
