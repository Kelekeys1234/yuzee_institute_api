package com.seeka.app.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "error_report")
public class ErrorReport implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4896547771928499529L;

    private BigInteger id;
    private BigInteger userId;
    private ErrorReportCategory errorReportCategory;
    private String description;
    private Date createdOn;
    private Date updatedOn;
    private String createdBy;
    private String updatedBy;
    private Date deletedOn;
    private Boolean isActive;
    
    private String caseNumber;
    private String status;
    private BigInteger courseArticleId;
    private Date dueDate;
    private BigInteger assigneeUserId;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public BigInteger getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", length = 19)
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", length = 19)
    public Date getUpdatedOn() {
        return this.updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_on", length = 19)
    public Date getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Column(name = "created_by", length = 50)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "updated_by", length = 50)
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the isActive
     */
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive
     *            the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the userId
     */
    @Column(name = "user_id")
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
     * @return the errorReportCategory
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    public ErrorReportCategory getErrorReportCategory() {
        return errorReportCategory;
    }

    /**
     * @param errorReportCategory
     *            the errorReportCategory to set
     */
    public void setErrorReportCategory(ErrorReportCategory errorReportCategory) {
        this.errorReportCategory = errorReportCategory;
    }

    /**
     * @return the description
     */
    @Column(name = "description")
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
     * @return the cashNumber
     */
    @Column(name = "case_number")
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
    @Column(name = "status")
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
    @Column(name = "course_article_id")
	public BigInteger getCourseArticleId() {
		return courseArticleId;
	}

	 /**
     * @param coreArticalDetail
     */
	public void setCourseArticleId(BigInteger courseArticleId) {
		this.courseArticleId = courseArticleId;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_date", length = 19)
	public Date getDueDate() {
		return dueDate;
	}

	 /**
     * @param dueDate
     */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	 /**
     * @return the assigneeUserId
     */
    @Column(name = "assignee_user_id")
	public BigInteger getAssigneeUserId() {
		return assigneeUserId;
	}

    /**
     * @param dueDate
     */
	public void setAssigneeUserId(BigInteger assigneeUserId) {
		this.assigneeUserId = assigneeUserId;
	}
    
    
}