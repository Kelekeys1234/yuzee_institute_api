package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReviewStarWrapperDto {

	private String message;
	private List<ReviewStarDto> data;
	private String status;
}