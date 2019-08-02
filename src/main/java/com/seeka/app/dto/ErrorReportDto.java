package com.seeka.app.dto;

import java.math.BigInteger;

public class ErrorReportDto {

    private BigInteger userId;
    private BigInteger errorReportCategoryId;
    private String description;
    private String createdBy;
    private String updatedBy;

    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    /**
     * @return the errorReportCategoryId
     */
    public BigInteger getErrorReportCategoryId() {
        return errorReportCategoryId;
    }

    /**
     * @param errorReportCategoryId
     *            the errorReportCategoryId to set
     */
    public void setErrorReportCategoryId(BigInteger errorReportCategoryId) {
        this.errorReportCategoryId = errorReportCategoryId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
