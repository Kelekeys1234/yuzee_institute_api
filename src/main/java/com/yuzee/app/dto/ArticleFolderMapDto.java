package com.yuzee.app.dto;import javax.validation.constraints.NotNull;

public class ArticleFolderMapDto {

    private String id;
    @NotNull(message="Folder Id is required")
    private String folderId;
    @NotNull(message="Article Id is required")
    private String articleId;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the folderId
     */
    public String getFolderId() {
        return folderId;
    }

    /**
     * @param folderId
     *            the folderId to set
     */
    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the articleId
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     *            the articleId to set
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}