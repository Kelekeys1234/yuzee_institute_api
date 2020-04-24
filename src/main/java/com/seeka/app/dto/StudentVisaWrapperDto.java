package com.seeka.app.dto;

import lombok.Data;

@Data
public class StudentVisaWrapperDto {
	public String message;
	public StudentVisaDto data;
	public String status;
}
