package com.seeka.app.dto;import java.math.BigInteger;

public class ArticleFolderMapDto {

    private BigInteger id;
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