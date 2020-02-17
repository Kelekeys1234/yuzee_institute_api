package com.seeka.app.dto;

import java.util.Date;

public class InstituteFilterDto {

    private String cityId;
    private String countryId;
    private String instituteId;
    private String instituteTypeId;

    public String getInstituteTypeId() {
        return instituteTypeId;
    }

    public void setInstituteTypeId(String instituteTypeId) {
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
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
