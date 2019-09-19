package com.seeka.app.dto;

import java.math.BigInteger;
import java.util.List;

public class EducationAgentPartnershipsDto {

    private BigInteger educationAgentId;
    private List<BigInteger> courseId;
    private List<BigInteger> instituteId;
    private String createdBy;
    private String UpdatedBy;
    private BigInteger country;

    public BigInteger getEducationAgentId() {
        return educationAgentId;
    }

    public void setEducationAgentId(BigInteger educationAgentId) {
        this.educationAgentId = educationAgentId;
    }

    public List<BigInteger> getCourseId() {
        return courseId;
    }

    public void setCourseId(List<BigInteger> courseId) {
        this.courseId = courseId;
    }

    public List<BigInteger> getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(List<BigInteger> instituteId) {
        this.instituteId = instituteId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public BigInteger getCountry() {
        return country;
    }

    public void setCountry(BigInteger country) {
        this.country = country;
    }
    
}
