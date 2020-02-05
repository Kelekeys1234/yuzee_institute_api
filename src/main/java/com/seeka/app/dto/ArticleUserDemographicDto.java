package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class ArticleUserDemographicDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6506522428374103956L;


	private String id;
	private String article;
	private ArticleCountryDto citizenship;
	private List<ArticleCityDto> cities;
	private String gender;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public ArticleCountryDto getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(ArticleCountryDto citizenship) {
		this.citizenship = citizenship;
	}
	public List<ArticleCityDto> getCities() {
		return cities;
	}
	public void setCities(List<ArticleCityDto> cities) {
		this.cities = cities;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
	
}
