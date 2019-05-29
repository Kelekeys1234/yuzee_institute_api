package com.seeka.app.dto;

import java.util.UUID;

public class ArticleNameDto {

    private UUID articleId;
    private String title;

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
