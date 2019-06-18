package com.seeka.app.dto;import java.math.BigInteger;

public class ArticleNameDto {

    private BigInteger articleId;
    private String title;

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

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
