package com.seeka.app.dto;

import java.util.List;

public class AdvanceSearchDto {

	private List<String> faculties;
	private List<String> levelIds;
	private List<String> serviceIds;
	private List<String> countryIds;
	private List<String> courseKeys;
	private List<String> cityIds;
	private Double minCost;
	private Double maxCost;
	private Integer minDuration;
	private Integer maxDuration;

	private boolean sortAsscending;
	private String sortBy;
	private Integer maxSizePerPage;
	private Integer pageNumber;
	private String currencyCode;
	private String userId;

	private String userCountryId;

	private List<String> names;
	private String searchKeyword;
	private List<String> partFulls;
	private List<String> deliveryMethods;
	private String instituteId;	
	
	public List<String> getPartFulls() {
		return partFulls;
	}

	public void setPartFulls(List<String> partFulls) {
		this.partFulls = partFulls;
	}

	public List<String> getDeliveryMethods() {
		return deliveryMethods;
	}

	public void setDeliveryMethods(List<String> deliveryMethods) {
		this.deliveryMethods = deliveryMethods;
	}

	/**
	 *
	 * @return
	 */
	public List<String> getNames() {
		return names;
	}

	/**
	 *
	 * @param names
	 */
	public void setNames(final List<String> names) {
		this.names = names;
	}

	/**
	 *
	 * @return
	 */
	public String getUserCountryId() {
		return userCountryId;
	}

	/**
	 *
	 * @param userCountryId
	 */
	public void setUserCountryId(final String userCountryId) {
		this.userCountryId = userCountryId;
	}

	/**
	 * @return the faculties
	 */
	public List<String> getFaculties() {
		return faculties;
	}

	/**
	 * @param faculties the faculties to set
	 */
	public void setFaculties(final List<String> faculties) {
		this.faculties = faculties;
	}

	/**
	 * @return the levelIds
	 */
	public List<String> getLevelIds() {
		return levelIds;
	}

	/**
	 * @param levelIds the levelIds to set
	 */
	public void setLevelIds(final List<String> levelIds) {
		this.levelIds = levelIds;
	}

	/**
	 * @return the serviceIds
	 */
	public List<String> getServiceIds() {
		return serviceIds;
	}

	/**
	 * @param serviceIds the serviceIds to set
	 */
	public void setServiceIds(final List<String> serviceIds) {
		this.serviceIds = serviceIds;
	}

	/**
	 * @return the minCost
	 */
	public Double getMinCost() {
		return minCost;
	}

	/**
	 * @param minCost the minCost to set
	 */
	public void setMinCost(final Double minCost) {
		this.minCost = minCost;
	}

	/**
	 * @return the maxCost
	 */
	public Double getMaxCost() {
		return maxCost;
	}

	/**
	 * @param maxCost the maxCost to set
	 */
	public void setMaxCost(final Double maxCost) {
		this.maxCost = maxCost;
	}

	/**
	 * @return the minDuration
	 */
	public Integer getMinDuration() {
		return minDuration;
	}

	/**
	 * @param minDuration the minDuration to set
	 */
	public void setMinDuration(final Integer minDuration) {
		this.minDuration = minDuration;
	}

	/**
	 * @return the maxDuration
	 */
	public Integer getMaxDuration() {
		return maxDuration;
	}

	/**
	 * @param maxDuration the maxDuration to set
	 */
	public void setMaxDuration(final Integer maxDuration) {
		this.maxDuration = maxDuration;
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
	public void setSortAsscending(final boolean sortAsscending) {
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
	public void setSortBy(final String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the countryIds
	 */
	public List<String> getCountryIds() {
		return countryIds;
	}

	/**
	 * @param countryIds the countryIds to set
	 */
	public void setCountryIds(final List<String> countryIds) {
		this.countryIds = countryIds;
	}

	/**
	 * @return the courseKeys
	 */
	public List<String> getCourseKeys() {
		return courseKeys;
	}

	/**
	 * @param courseKeys the courseKeys to set
	 */
	public void setCourseKeys(final List<String> courseKeys) {
		this.courseKeys = courseKeys;
	}

	/**
	 * @return the maxSizePerPage
	 */
	public Integer getMaxSizePerPage() {
		return maxSizePerPage;
	}

	/**
	 * @param maxSizePerPage the maxSizePerPage to set
	 */
	public void setMaxSizePerPage(final Integer maxSizePerPage) {
		this.maxSizePerPage = maxSizePerPage;
	}

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(final Integer pageNumber) {
		this.pageNumber = pageNumber;
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
	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * @return the cityIds
	 */
	public List<String> getCityIds() {
		return cityIds;
	}

	/**
	 * @param cityIds the cityIds to set
	 */
	public void setCityIds(final List<String> cityIds) {
		this.cityIds = cityIds;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(final String searchKeyword) {
		this.searchKeyword = searchKeyword;
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
		builder.append("AdvanceSearchDto [faculties=").append(faculties).append(", levelIds=").append(levelIds).append(", serviceIds=").append(serviceIds)
				.append(", countryIds=").append(countryIds).append(", courseKeys=").append(courseKeys).append(", cityIds=").append(cityIds).append(", minCost=")
				.append(minCost).append(", maxCost=").append(maxCost).append(", minDuration=").append(minDuration).append(", maxDuration=").append(maxDuration)
				.append(", sortAsscending=").append(sortAsscending).append(", sortBy=").append(sortBy).append(", maxSizePerPage=").append(maxSizePerPage)
				.append(", pageNumber=").append(pageNumber).append(", currencyCode=").append(currencyCode).append(", userId=").append(userId)
				.append(", userCountryId=").append(userCountryId).append(", names=").append(names).append(", searchKeyword=").append(searchKeyword)
				.append(", partFulls=").append(partFulls).append(", deliveryMethods=").append(deliveryMethods).append(", instituteId=").append(instituteId)
				.append("]");
		return builder.toString();
	}

}
