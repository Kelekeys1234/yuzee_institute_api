package com.seeka.app.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "article_user_demographic")
public class ArticleUserDemographic implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5046135699948169230L;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", columnDefinition = "uniqueidentifier")
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = true)
	@JoinColumn(name = "article_id", nullable = false)	
	private SeekaArticles article;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = true)
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, optional = true)
	@JoinColumn(name = "city_id", nullable = false)
	private City city;
	
	@Column(name = "gender")
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "created_by")
	private BigInteger createdBy;
	@Column(name = "updated_by")
	private BigInteger updatedBy;
	
	
	
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SeekaArticles getArticle() {
		return article;
	}
	public void setArticle(SeekaArticles article) {
		this.article = article;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public BigInteger getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}
	public BigInteger getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(BigInteger updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
