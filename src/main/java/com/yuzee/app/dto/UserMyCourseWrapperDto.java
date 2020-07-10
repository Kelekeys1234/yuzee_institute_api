package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserMyCourseWrapperDto {
	public String message;
	public List<UserMyCourseDto> data;
	public String status;

}
