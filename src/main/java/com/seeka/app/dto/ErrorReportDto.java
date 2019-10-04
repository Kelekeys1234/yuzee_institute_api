package com.seeka.app.dto;

import java.math.BigInteger;

public class ErrorReportDto {

    private BigInteger userId;
    private BigInteger errorReportCategoryId;
    private String description;
    private String createdBy;
    private String updatedBy;
    
    private String caseNumber;
    private String status;
    private BigInteger courseArticleId;
    private String dueDate;
    private BigInteger assigneeUserId;
    private String severity;

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

    /**
     * @return the cashNumber
     */
	public String getCaseNumber() {
		return caseNumber;
	}

	 /**
     * @param cashNumber
     */
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	/**
     * @return the status
     */
	public String getStatus() {
		return status;
	}

	 /**
     * @param status
     */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
     * @return the coreArticalDetail
     */
	public BigInteger getCourseArticleId() {
		return courseArticleId;
	}


	 /**
    * @param coreArticalDetail
    */
	public void setCourseArticleId(BigInteger courseArticleId) {
		this.courseArticleId = courseArticleId;
	}

	/**
     * @return the dueDate
     */
	public String getDueDate() {
		return dueDate;
	}

	/**
     * @param dueDate
     */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	/**
     * @return the assigneeUserId
     */
	public BigInteger getAssigneeUserId() {
		return assigneeUserId;
	}

	/**
     * @param assigneeUserId
     */
	public void setAssigneeUserId(BigInteger assigneeUserId) {
		this.assigneeUserId = assigneeUserId;
	}

    /**
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    

}
