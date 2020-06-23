package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class CourseSearchDto {
	private String searchKey;
	private String courseName;
	private Boolean isProfileSearch;
	private String currencyId;
	private String userId;
	private List<String> courseKeys;
	private List<String> levelIds;
	private List<String> facultyIds;
	private List<String> countryNames;
	private List<String> serviceIds;
	private List<String> cityNames;
	private Double minCost;
	private Double maxCost;
	private Integer minDuration;
	private Integer maxDuration;
	private Integer maxSizePerPage;
	private Integer pageNumber;
	private CourseSearchFilterDto sortingObj;
	private String instituteId;
	private Boolean sortAsscending;
	private String sortBy;
	private String currencyCode;
	private String date;
	private Double latitude;
	private Double longitude;
}
