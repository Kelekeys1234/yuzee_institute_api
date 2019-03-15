package com.seeka.freshfuture.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SearchKeywords implements Serializable {
	
	private Integer id;
	
	@Column(name="keyword")
	private String keyword; // keyword
	
	@Column(name="k_desc")
	private String keywordDescription; // keyword description
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeywordDescription() {
		return keywordDescription;
	}

	public void setKeywordDescription(String keywordDescription) {
		this.keywordDescription = keywordDescription;
	}
	
	

}
