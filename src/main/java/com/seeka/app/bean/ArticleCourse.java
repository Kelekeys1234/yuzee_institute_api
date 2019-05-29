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
@Table(name = "article_course")
public class ArticleCourse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID articleId;
    private UUID courseId;

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

    /**
     * @return the courseId
     */
    @Column(name = "course_id")
    @Type(type = "uuid-char")
    public UUID getCourseId() {
        return courseId;
    }

    /**
     * @param courseId
     *            the courseId to set
     */
    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }
}