package com.seeka.app.dto;import java.math.BigInteger;

public class ArticleFolderDto {
    
    private BigInteger id;
    private String folderName;
    /**
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(BigInteger id) {
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

}
