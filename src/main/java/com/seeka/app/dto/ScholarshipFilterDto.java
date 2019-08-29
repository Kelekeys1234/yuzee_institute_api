package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;

public class ScholarshipFilterDto {
    private BigInteger cityId;
    private BigInteger countryId;
    private BigInteger faculty;
    private BigInteger level;
    private String type;
    private String coverage;
    private Date datePosted;
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
    public BigInteger getFaculty() {
        return faculty;
    }
    public void setFaculty(BigInteger faculty) {
        this.faculty = faculty;
    }
    public BigInteger getLevel() {
        return level;
    }
    public void setLevel(BigInteger level) {
        this.level = level;
    }
    public String getCoverage() {
        return coverage;
    }
    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
    public Date getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}
