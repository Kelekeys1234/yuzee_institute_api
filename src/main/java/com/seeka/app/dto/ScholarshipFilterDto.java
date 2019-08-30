package com.seeka.app.dto;

import java.math.BigInteger;

public class ScholarshipFilterDto {
    private BigInteger cityId;
    private BigInteger countryId;
    private BigInteger facultyId;
    private BigInteger levelId;
    private BigInteger instituteId;
    private String type;
    private String coverage;
    private String datePosted;
    private Integer maxSizePerPage;
    private Integer pageNumber;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    /**
     * @return the facultyId
     */
    public BigInteger getFacultyId() {
        return facultyId;
    }

    /**
     * @param facultyId
     *            the facultyId to set
     */
    public void setFacultyId(BigInteger facultyId) {
        this.facultyId = facultyId;
    }

    /**
     * @return the levelId
     */
    public BigInteger getLevelId() {
        return levelId;
    }

    /**
     * @param levelId
     *            the levelId to set
     */
    public void setLevelId(BigInteger levelId) {
        this.levelId = levelId;
    }

    /**
     * @return the instituteId
     */
    public BigInteger getInstituteId() {
        return instituteId;
    }

    /**
     * @param instituteId
     *            the instituteId to set
     */
    public void setInstituteId(BigInteger instituteId) {
        this.instituteId = instituteId;
    }
}
