package com.yuzee.app.dto;

import lombok.Data;

@Data
public class CommonDtoWrapper {
	public String message;
	public YouTubeResponseDto data;
	public String status;
}
