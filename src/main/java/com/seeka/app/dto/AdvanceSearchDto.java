package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class AdvanceSearchDto {

    private List<BigInteger> faculties;
    private List<BigInteger> levelIds;
    private List<BigInteger> serviceIds;
    private List<BigInteger> countryIds;
    private List<String> courseKeys;
    private List<BigInteger> cityIds;
    private Double minCost;
    private Double maxCost;
    private Integer minDuration;
    private Integer maxDuration;

    private boolean sortAsscending;
    private String sortBy;
    private Integer maxSizePerPage;
    private Integer pageNumber;
    private String currencyCode;
    private BigInteger userId;

    /**
     * @return the faculties
     */
    public List<BigInteger> getFaculties() {
        return faculties;
    }

    /**
     * @param faculties
     *            the faculties to set
     */
    public void setFaculties(List<BigInteger> faculties) {
        this.faculties = faculties;
    }

    /**
     * @return the levelIds
     */
    public List<BigInteger> getLevelIds() {
        return levelIds;
    }

    /**
     * @param levelIds
     *            the levelIds to set
     */
    public void setLevelIds(List<BigInteger> levelIds) {
        this.levelIds = levelIds;
    }

    /**
     * @return the serviceIds
     */
    public List<BigInteger> getServiceIds() {
        return serviceIds;
    }

    /**
     * @param serviceIds
     *            the serviceIds to set
     */
    public void setServiceIds(List<BigInteger> serviceIds) {
        this.serviceIds = serviceIds;
    }

    /**
     * @return the minCost
     */
    public Double getMinCost() {
        return minCost;
    }

    /**
     * @param minCost
     *            the minCost to set
     */
    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }

    /**
     * @return the maxCost
     */
    public Double getMaxCost() {
        return maxCost;
    }

    /**
     * @param maxCost
     *            the maxCost to set
     */
    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    /**
     * @return the minDuration
     */
    public Integer getMinDuration() {
        return minDuration;
    }

    /**
     * @param minDuration
     *            the minDuration to set
     */
    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    /**
     * @return the maxDuration
     */
    public Integer getMaxDuration() {
        return maxDuration;
    }

    /**
     * @param maxDuration
     *            the maxDuration to set
     */
    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    /**
     * @return the sortAsscending
     */
    public boolean getSortAsscending() {
        return sortAsscending;
    }

    /**
     * @param sortAsscending
     *            the sortAsscending to set
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
     * @param sortBy
     *            the sortBy to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the countryIds
     */
    public List<BigInteger> getCountryIds() {
        return countryIds;
    }

    /**
     * @param countryIds the countryIds to set
     */
    public void setCountryIds(List<BigInteger> countryIds) {
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
    public void setCourseKeys(List<String> courseKeys) {
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
    public void setMaxSizePerPage(Integer maxSizePerPage) {
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
    public void setPageNumber(Integer pageNumber) {
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
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    /**
     * @return the cityIds
     */
    public List<BigInteger> getCityIds() {
        return cityIds;
    }

    /**
     * @param cityIds the cityIds to set
     */
    public void setCityIds(List<BigInteger> cityIds) {
        this.cityIds = cityIds;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdvanceSearchDto [faculties=").append(faculties).append(", levelIds=").append(levelIds)
				.append(", serviceIds=").append(serviceIds).append(", countryIds=").append(countryIds)
				.append(", courseKeys=").append(courseKeys).append(", cityIds=").append(cityIds).append(", minCost=")
				.append(minCost).append(", maxCost=").append(maxCost).append(", minDuration=").append(minDuration)
				.append(", maxDuration=").append(maxDuration).append(", sortAsscending=").append(sortAsscending)
				.append(", sortBy=").append(sortBy).append(", maxSizePerPage=").append(maxSizePerPage)
				.append(", pageNumber=").append(pageNumber).append(", currencyCode=").append(currencyCode)
				.append(", userId=").append(userId).append("]");
		return builder.toString();
	}

    
}
