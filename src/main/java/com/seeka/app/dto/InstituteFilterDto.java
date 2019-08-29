package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.Date;

public class InstituteFilterDto {
    
    private BigInteger cityId;
    private BigInteger countryId;
    private BigInteger instituteId;
    private String type;
    private Date durationDate;
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
    public BigInteger getInstituteId() {
        return instituteId;
    }
    public void setInstituteId(BigInteger instituteId) {
        this.instituteId = instituteId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
}
