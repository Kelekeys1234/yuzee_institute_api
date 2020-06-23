package com.seeka.app.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class InstituteResponseDto {
	private String id;
	private String name;
	private Integer worldRanking;
	private String location;
	private Integer totalCourses;
	private Integer totalCount;
	private String website;
	private String aboutUs;
	private String openingFrom;
	private String openingTo;
	private Integer totalStudent;
	private Double latitude;
	private Double longitude;
	private String phoneNumber;
	private String email;
	private String address;
	private String visaRequirement;
	private String totalAvailableJobs;
	private String countryName;
	private String cityName;
	private Date updatedOn;
	private String instituteType;
	private List<StorageDto> storageList;
	private Boolean isActive;
	private Integer stars;
	private Integer domesticRanking;
	private Double distance;
	private Double minPriceRange;
	private Double maxPriceRange;
	private String currency;
	private List<String> instituteServices;
	private List<AccrediatedDetailDto> accrediatedDetail;
}
