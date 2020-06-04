package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class NearestCourseResponseDto {
	private String id;
	private String name;
	private Integer courseRanking;
	private Double stars;
	private Double duration;
	private String durationTime;
	private String language;
	private String instituteId;
	private String instituteName;
	private Double costRange;
	private Double domesticFee;
	private Double internationalFee;
	private String requirements;
	private String countryName;
	private String cityName;
	private String currencyCode;
	private List<StorageDto> storageList;
	private Double latitude;
	private Double longitude;
	private Integer radius;
}
