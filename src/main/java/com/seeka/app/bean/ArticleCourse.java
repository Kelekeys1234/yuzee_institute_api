package com.seeka.app.bean;import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ArticleCourse generated by hbm2java
 */
@Entity
@Table(name = "article_course")
public class ArticleCourse implements java.io.Serializable {

	private BigInteger id;
	private Course course;
	private SeekaArticles seekaArticles;

	public ArticleCourse() {
	}

	public ArticleCourse(Course course, SeekaArticles seekaArticles) {
		this.course = course;
		this.seekaArticles = seekaArticles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "article_id")
	public SeekaArticles getSeekaArticles() {
		return this.seekaArticles;
	}

	public void setSeekaArticles(SeekaArticles seekaArticles) {
		this.seekaArticles = seekaArticles;
	}

}
