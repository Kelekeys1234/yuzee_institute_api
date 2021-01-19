package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class FundingWrapperDto {
	private String message;

	private List<FundingResponseDto> data;

	private String status;
}
