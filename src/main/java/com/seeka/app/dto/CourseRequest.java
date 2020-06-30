package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseRequest {

	private String id;
	private String instituteId;
	private String facultyId;
	private String name;
	private String description;
	private List<Date> intake;
	private List<String> language;
	private String grades;
	private String documentUrl;
	
	private String contact;
	private String openingHourFrom;
	private String openingHourTo;
	private String jobFullTime;
	private String jobPartTime;
	private String campusLocation;
	private String website;
	
	private String link;
	private String lastUpdated;
	private String instituteName;
	private String location;
	private String worldRanking;
	private String stars;
	private String cost;
	private String totalCount;
	
	private String requirements;
	private String currency;
	private String currencyTime;
	private String facultyName;
	private String levelId;
	private String levelName;
	private String availbility;
	private String recognition;
	private String recognitionType;
	private String abbreviation;
	
	private List<CourseEnglishEligibilityDto> englishEligibility;
	private List<StorageDto> storageList;

	private String remarks;
	private Boolean applied;
	private Boolean viewCourse;
	private Double latitude;
	private Double longitude;
	private List<UserReviewResultDto> userReviewResult;
	
	private String countryName;
	private String cityName;
	private StudentVisaDto studentVisa;
	private List<AccrediatedDetailDto> accrediatedDetail;
	private List<CourseAdditionalInfoDto> courseAdditionalInfo;
}
