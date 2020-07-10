package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserInstituteAccessRoleInternalResponseDtoWrapper {

	private String message;

	private String status;
	
    private List<UserInstituteAccessInternalResponseDto> data; 
}
