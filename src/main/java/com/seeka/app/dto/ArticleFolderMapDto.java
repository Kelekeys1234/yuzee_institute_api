package com.seeka.app.dto;

import java.util.UUID;

public class ArticleFolderMapDto {

    private UUID id;
    private UUID userId;
    private UUID folderId;
    private UUID articleId;

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * @return the folderId
     */
    public UUID getFolderId() {
        return folderId;
    }

    /**
     * @param folderId
     *            the folderId to set
     */
    public void setFolderId(UUID folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the articleId
     */
    public UUID getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     *            the articleId to set
     */
    public void setArticleId(UUID articleId) {
        this.articleId = articleId;
    }
}