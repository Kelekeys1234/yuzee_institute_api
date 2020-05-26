package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class NearestCoursesDto {

	private String id;
	private String name;
	private Double domesticFee;
	private Double internationalFee;
	private List<StorageDto> courseLogoImages;
}
