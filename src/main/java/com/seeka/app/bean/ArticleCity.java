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
 * ArticleCity generated by hbm2java
 */
@Entity
@Table(name = "article_city", catalog = "seeka_dev_v5")
public class ArticleCity implements java.io.Serializable {

	private BigInteger id;
	private City city;
	private SeekaArticles seekaArticles;

	public ArticleCity() {
	}

	public ArticleCity(City city, SeekaArticles seekaArticles) {
		this.city = city;
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
	@JoinColumn(name = "city_id")
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
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
