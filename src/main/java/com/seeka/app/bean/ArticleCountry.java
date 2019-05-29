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
@Table(name = "article_country")
public class ArticleCountry implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID articleId;
    private UUID country;

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

    @Column(name = "country_id")
    @Type(type = "uuid-char")
    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
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