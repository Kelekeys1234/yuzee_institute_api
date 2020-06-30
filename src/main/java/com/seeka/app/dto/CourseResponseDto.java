package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseResponseDto {

	private String id;
	private String name;
	private Integer courseRanking;
	private Double stars;
	private Double duration;
	private String durationTime;
	private List<String> language;

	private String languageShortKey;
	private String instituteId;
	private String instituteName;
	private Double costRange;

	private String location;
	private Integer totalCount;
	private Double domesticFee;
	private Double internationalFee;
	private String requirements;
	private String countryName;
	private String cityName;
	private Boolean isFavourite;
	
	private String currencyCode;
	private List<StorageDto> storageList;
	private Boolean isViewed = false;
	private String cost;
	private Boolean isActive;
	private Date updatedOn;
	private List<Date> intake;
	private List<String> deliveryMethod;

	private String facultyName;
	private String facultyId;
	private Double distance;
	private Double latitude;
	private Double longitude;
	private List<CourseAdditionalInfoDto> courseAdditionalInfo;
}
