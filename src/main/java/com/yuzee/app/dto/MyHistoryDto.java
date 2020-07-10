package com.yuzee.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class MyHistoryDto {
	private String id;
	private String name;
	private String instituteId;
	private String instituteName;
	private String cityName;
	private String countryName;
	private Double stars;
	private List<StorageDto> storage;
}
