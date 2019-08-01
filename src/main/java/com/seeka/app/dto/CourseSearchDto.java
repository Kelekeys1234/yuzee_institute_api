package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class CourseSearchDto {

	private String searchKey;
	private String courseName;
	private Boolean isProfileSearch;
	private BigInteger currencyId;
	private BigInteger userId;
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
	private BigInteger instituteId;

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(final String searchKey) {
		this.searchKey = searchKey;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(final String courseName) {
		this.courseName = courseName;
	}

	public Boolean getIsProfileSearch() {
		return isProfileSearch;
	}

	public void setIsProfileSearch(final Boolean isProfileSearch) {
		this.isProfileSearch = isProfileSearch;
	}

	public List<String> getCourseKeys() {
		return courseKeys;
	}

	public void setCourseKeys(final List<String> courseKeys) {
		this.courseKeys = courseKeys;
	}

	public List<BigInteger> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(final List<BigInteger> levelIds) {
		this.levelIds = levelIds;
	}

	public List<BigInteger> getFacultyIds() {
		return facultyIds;
	}

	public void setFacultyIds(final List<BigInteger> facultyIds) {
		this.facultyIds = facultyIds;
	}

	public List<BigInteger> getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(final List<BigInteger> countryIds) {
		this.countryIds = countryIds;
	}

	public List<BigInteger> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(final List<BigInteger> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public Double getMinCost() {
		return minCost;
	}

	public void setMinCost(final Double minCost) {
		this.minCost = minCost;
	}

	public Double getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(final Double maxCost) {
		this.maxCost = maxCost;
	}

	public Integer getMinDuration() {
		return minDuration;
	}

	public void setMinDuration(final Integer minDuration) {
		this.minDuration = minDuration;
	}

	public Integer getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(final Integer maxDuration) {
		this.maxDuration = maxDuration;
	}

	public CourseSearchFilterDto getSortingObj() {
		return sortingObj;
	}

	public void setSortingObj(final CourseSearchFilterDto sortingObj) {
		this.sortingObj = sortingObj;
	}

	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}

	public void setMaxSizePerPage(final Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(final Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(final BigInteger currencyId) {
		this.currencyId = currencyId;
	}

	public List<BigInteger> getCityIds() {
		return cityIds;
	}

	public void setCityIds(final List<BigInteger> cityIds) {
		this.cityIds = cityIds;
	}

	public BigInteger getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final BigInteger instituteId) {
		this.instituteId = instituteId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CourseSearchDto [searchKey=").append(searchKey).append(", courseName=").append(courseName).append(", isProfileSearch=")
				.append(isProfileSearch).append(", userId=").append(userId).append(", currencyId=").append(currencyId).append(", courseKeys=")
				.append(courseKeys).append(", levelIds=").append(levelIds).append(", facultyIds=").append(facultyIds).append(", countryIds=").append(countryIds)
				.append(", serviceIds=").append(serviceIds).append(", cityIds=").append(cityIds).append(", minCost=").append(minCost).append(", maxCost=")
				.append(maxCost).append(", minDuration=").append(minDuration).append(", maxDuration=").append(maxDuration).append(", maxSizePerPage=")
				.append(maxSizePerPage).append(", pageNumber=").append(pageNumber).append(", sortingObj=").append(sortingObj).append(", instituteId=")
				.append(instituteId).append("]");
		return builder.toString();
	}

}
