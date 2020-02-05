package com.seeka.app.dto;import java.math.BigInteger;

public class ArticleFolderDto {
    
    private String id;
    private String folderName;
    private BigInteger userId;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the folderName
     */
    public String getFolderName() {
        return folderName;
    }
    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    /**
     * @return the userId
     */
    public BigInteger getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

}
