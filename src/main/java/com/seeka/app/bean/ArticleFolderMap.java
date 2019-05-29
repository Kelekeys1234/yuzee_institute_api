package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "article_folder_map")
public class ArticleFolderMap implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private UUID folderId;
    private UUID articleId;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id", updatable = false, nullable = false)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    @Column(name = "user_id")
    @Type(type = "uuid-char")
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
    @Column(name = "folder_id")
    @Type(type = "uuid-char")
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
    @Column(name = "article_id")
    @Type(type = "uuid-char")
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