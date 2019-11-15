package com.seeka.app.dto;import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class ArticleFolderMapDto {

    private BigInteger id;
    @NotNull(message="Folder Id is required")
    private BigInteger folderId;
    @NotNull(message="Article Id is required")
    private BigInteger articleId;

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
     * @return the folderId
     */
    public BigInteger getFolderId() {
        return folderId;
    }

    /**
     * @param folderId
     *            the folderId to set
     */
    public void setFolderId(BigInteger folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the articleId
     */
    public BigInteger getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     *            the articleId to set
     */
    public void setArticleId(BigInteger articleId) {
        this.articleId = articleId;
    }
}