package com.seeka.app.dto;import java.math.BigInteger;

public class ArticleFolderMapDto {

    private BigInteger id;
    private BigInteger userId;
    private BigInteger folderId;
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
     * @return the userId
     */
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