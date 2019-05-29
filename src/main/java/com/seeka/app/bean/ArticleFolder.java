package com.seeka.app.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.seeka.app.dto.ArticleNameDto;

@Entity
@Table(name = "article_folder")
public class ArticleFolder implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String folderName;
    private Boolean deleted;
    private Date createdAt;
    private Date updatedAt;
    private List<ArticleNameDto> articles;
    private UUID userId;

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
     * @return the folderName
     */
    @Column(name = "folder_name")
    public String getFolderName() {
        return folderName;
    }

    /**
     * @param folderName
     *            the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * @return the deleted
     */
    @Column(name = "deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the createdAt
     */
    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     *            the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the updatedAt
     */
    @Column(name = "updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     *            the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the articles
     */
    @Transient
    public List<ArticleNameDto> getArticles() {
        return articles;
    }

    /**
     * @param articles the articles to set
     */
    public void setArticles(List<ArticleNameDto> articles) {
        this.articles = articles;
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
     * @param userId the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}