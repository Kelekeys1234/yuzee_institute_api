package com.seeka.app.dto;

import java.util.List;

public class CourseSearchDto {

	private String searchKey;
	private String courseName;
	private Boolean isProfileSearch;
	private String currencyId;
	private String userId;
	private List<String> courseKeys;
	private List<String> levelIds;
	private List<String> facultyIds;
	private List<String> countryIds;
	private List<String> serviceIds;
	private List<String> cityIds;
	private Double minCost;
	private Double maxCost;
	private Integer minDuration;
	private Integer maxDuration;
	private Integer maxSizePerPage;
	private Integer pageNumber;
	private CourseSearchFilterDto sortingObj;
	private String instituteId;
	private boolean sortAsscending;
	private String sortBy;
	private String currencyCode;
	private String date;

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

	public List<String> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(final List<String> levelIds) {
		this.levelIds = levelIds;
	}

	public List<String> getFacultyIds() {
		return facultyIds;
	}

	public void setFacultyIds(final List<String> facultyIds) {
		this.facultyIds = facultyIds;
	}

	public List<String> getCountryIds() {
		return countryIds;
	}

	public void setCountryIds(final List<String> countryIds) {
		this.countryIds = countryIds;
	}

	public List<String> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(final List<String> serviceIds) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(final String currencyId) {
		this.currencyId = currencyId;
	}

	public List<String> getCityIds() {
		return cityIds;
	}

	public void setCityIds(final List<String> cityIds) {
		this.cityIds = cityIds;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(final String instituteId) {
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

    /**
     * @return the sortAsscending
     */
    public boolean getSortAsscending() {
        return sortAsscending;
    }

    /**
     * @param sortAsscending the sortAsscending to set
     */
    public void setSortAsscending(boolean sortAsscending) {
        this.sortAsscending = sortAsscending;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

}
