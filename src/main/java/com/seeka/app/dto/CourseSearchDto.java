package com.seeka.app.dto;

import java.util.List;
import java.util.UUID;

public class CourseSearchDto {
	
	private String searchKey;
	private String courseName;
	private Boolean isProfileSearch;
	private UUID userId;
	private UUID currencyId;
	private List<String> courseKeys;
	private List<UUID> levelIds;
	private List<UUID> facultyIds;
	private List<UUID> countryIds;
	private List<UUID> serviceIds;
	private Double minCost;
	private Double maxCost;
	private Integer minDuration;
	private Integer maxDuration;
	private Integer maxSizePerPage;
	private Integer pageNumber;
	private CourseSearchFilterDto sortingObj;
	
	
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	 
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Boolean getIsProfileSearch() {
		return isProfileSearch;
	}
	public void setIsProfileSearch(Boolean isProfileSearch) {
		this.isProfileSearch = isProfileSearch;
	}
	public List<String> getCourseKeys() {
		return courseKeys;
	}
	public void setCourseKeys(List<String> courseKeys) {
		this.courseKeys = courseKeys;
	}
	public List<UUID> getLevelIds() {
		return levelIds;
	}
	public void setLevelIds(List<UUID> levelIds) {
		this.levelIds = levelIds;
	}
	public List<UUID> getFacultyIds() {
		return facultyIds;
	}
	public void setFacultyIds(List<UUID> facultyIds) {
		this.facultyIds = facultyIds;
	}
	public List<UUID> getCountryIds() {
		return countryIds;
	}
	public void setCountryIds(List<UUID> countryIds) {
		this.countryIds = countryIds;
	}
	public List<UUID> getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(List<UUID> serviceIds) {
		this.serviceIds = serviceIds;
	}
	public Double getMinCost() {
		return minCost;
	}
	public void setMinCost(Double minCost) {
		this.minCost = minCost;
	}
	public Double getMaxCost() {
		return maxCost;
	}
	public void setMaxCost(Double maxCost) {
		this.maxCost = maxCost;
	}
	public Integer getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}
	public Integer getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}

	public CourseSearchFilterDto getSortingObj() {
		return sortingObj;
	}
	public void setSortingObj(CourseSearchFilterDto sortingObj) {
		this.sortingObj = sortingObj;
	}
	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}
	public void setMaxSizePerPage(Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public UUID getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(UUID currencyId) {
		this.currencyId = currencyId;
	}
	 
}
