package com.seeka.app.dto;

import java.math.BigInteger;

public class HelpDto {

    private BigInteger id;
    private String title;
    private BigInteger categoryId;
    private BigInteger subCategoryId;
    private String description;
    private String createdBy;
    private String updatedBy;
    private Boolean isQuestioning;
    private String createdUser;
    private String Status;
    private String assignedUser;
    private String createdOn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public BigInteger getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(BigInteger subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getIsQuestioning() {
        return isQuestioning;
    }

    public void setIsQuestioning(Boolean isQuestioning) {
        this.isQuestioning = isQuestioning;
    }

    /**
     * @return the id
     */
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

    /**
     * @return the createdUser
     */
    public String getCreatedUser() {
        return createdUser;
    }

    /**
     * @param createdUser
     *            the createdUser to set
     */
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        Status = status;
    }

    /**
     * @return the assignedUser
     */
    public String getAssignedUser() {
        return assignedUser;
    }

    /**
     * @param assignedUser
     *            the assignedUser to set
     */
    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * @return the createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
