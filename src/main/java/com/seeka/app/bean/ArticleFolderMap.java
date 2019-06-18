package com.seeka.app.bean;import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article_folder_map")
public class ArticleFolderMap implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BigInteger id;
    private BigInteger userId;
    private BigInteger folderId;
    private BigInteger articleId;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    @Column(name = "user_id")
    
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
    @Column(name = "folder_id")
    
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
    @Column(name = "article_id")
    
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