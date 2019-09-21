package com.seeka.app.dto;

import java.math.BigInteger;

public class EducationAgentPartnershipsDto {

    private BigInteger agentId;
    private BigInteger courseId;
    private BigInteger instituteId;
    private String createdBy;
    private String UpdatedBy;
    private BigInteger countryId;

    /**
     * @return the agentId
     */
    public BigInteger getAgentId() {
        return agentId;
    }

    /**
     * @param agentId
     *            the agentId to set
     */
    public void setAgentId(BigInteger agentId) {
        this.agentId = agentId;
    }

    /**
     * @return the courseId
     */
    public BigInteger getCourseId() {
        return courseId;
    }

    /**
     * @param courseId
     *            the courseId to set
     */
    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
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

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return UpdatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    /**
     * @return the countryId
     */
    public BigInteger getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(BigInteger countryId) {
        this.countryId = countryId;
    }

}
