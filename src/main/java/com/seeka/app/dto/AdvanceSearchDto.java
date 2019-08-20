package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class AdvanceSearchDto {

    private List<BigInteger> faculties;
    private List<BigInteger> levelIds;
    private List<BigInteger> serviceIds;
    private Double minCost;
    private Double maxCost;
    private Integer minDuration;
    private Integer maxDuration;

    private boolean sortAsscending;
    private String sortBy;

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
    public boolean isSortAsscending() {
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

}
