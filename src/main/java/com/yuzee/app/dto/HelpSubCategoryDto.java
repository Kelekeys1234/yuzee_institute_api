package com.yuzee.app.dto;

public class HelpSubCategoryDto {

    private String id;
    private String name;
    private String createdBy;
    private String updatedBy;
    private String categoryId;
    private Integer helpCount;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
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
     * @return the helpCount
     */
    public Integer getHelpCount() {
        return helpCount;
    }

    /**
     * @param helpCount the helpCount to set
     */
    public void setHelpCount(Integer helpCount) {
        this.helpCount = helpCount;
    }


}
