package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;

public class InstituteFilterDto {

    private BigInteger cityId;
    private BigInteger countryId;
    private BigInteger instituteId;
    private BigInteger instituteTypeId;

    public BigInteger getInstituteTypeId() {
        return instituteTypeId;
    }

    public void setInstituteTypeId(BigInteger instituteTypeId) {
        this.instituteTypeId = instituteTypeId;
    }

    private Date durationDate;
    private Integer maxSizePerPage;
    private Integer pageNumber;
    private Integer worldRanking;
    private String datePosted;

    public Integer getWorldRanking() {
        return worldRanking;
    }

    public void setWorldRanking(Integer worldRanking) {
        this.worldRanking = worldRanking;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public BigInteger getCountryId() {
        return countryId;
    }

    public void setCountryId(BigInteger countryId) {
        this.countryId = countryId;
    }

    public BigInteger getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(BigInteger instituteId) {
        this.instituteId = instituteId;
    }

    public Date getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(Date durationDate) {
        this.durationDate = durationDate;
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

    /**
     * @return the datePosted
     */
    public String getDatePosted() {
        return datePosted;
    }

    /**
     * @param datePosted the datePosted to set
     */
    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
}
