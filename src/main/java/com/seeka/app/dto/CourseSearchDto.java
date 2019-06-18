package com.seeka.app.dto;import java.math.BigInteger;

import java.util.List;


public class CourseSearchDto {
	
	private String searchKey;
	private String courseName;
	private Boolean isProfileSearch;
	private BigInteger userId;
	private BigInteger currencyId;
	private List<String> courseKeys;
	private List<BigInteger> levelIds;
	private List<BigInteger> facultyIds;
	private List<BigInteger> countryIds;
	private List<BigInteger> serviceIds;
	private List<BigInteger> cityIds;
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
	public List<BigInteger> getLevelIds() {
		return levelIds;
	}
	public void setLevelIds(List<BigInteger> levelIds) {
		this.levelIds = levelIds;
	}
	public List<BigInteger> getFacultyIds() {
		return facultyIds;
	}
	public void setFacultyIds(List<BigInteger> facultyIds) {
		this.facultyIds = facultyIds;
	}
	public List<BigInteger> getCountryIds() {
		return countryIds;
	}
	public void setCountryIds(List<BigInteger> countryIds) {
		this.countryIds = countryIds;
	}
	public List<BigInteger> getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(List<BigInteger> serviceIds) {
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
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public BigInteger getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigInteger currencyId) {
		this.currencyId = currencyId;
	}
	public List<BigInteger> getCityIds() {
		return cityIds;
	}
	public void setCityIds(List<BigInteger> cityIds) {
		this.cityIds = cityIds;
	}
	 
}
