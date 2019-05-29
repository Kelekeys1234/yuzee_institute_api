package com.seeka.app.dto;

import java.util.UUID;

public class ArticleFolderDto {
    
    private UUID id;
    private String folderName;
    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
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
