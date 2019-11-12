package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class ArticleUserDemographicDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6506522428374103956L;


	private Long id;
	private BigInteger article;
	private BigInteger country;
	private List<BigInteger> city;
	private String gender;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigInteger getArticle() {
		return article;
	}
	public void setArticle(BigInteger article) {
		this.article = article;
	}
	public BigInteger getCountry() {
		return country;
	}
	public void setCountry(BigInteger country) {
		this.country = country;
	}
	public List<BigInteger> getCity() {
		return city;
	}
	public void setCity(List<BigInteger> city) {
		this.city = city;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}


	
	
	
	
	
}
