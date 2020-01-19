package com.seeka.app.dto;

import java.math.BigInteger;

public class HelpAnswerDto {

    private BigInteger userId;
    private BigInteger helpId;
    private String answer;
    private String createdBy;
    private String updatedBy;
    private String createdOn;
    private String fileUrl;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getHelpId() {
        return helpId;
    }

    public void setHelpId(BigInteger helpId) {
        this.helpId = helpId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    /**
     * @return the createdDate
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the fileUrl
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * @param fileUrl the fileUrl to set
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

   
}
