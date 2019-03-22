package com.seeka.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name="search_keywords")
public class SearchKeywords implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="keyword")
	private String keyword; // keyword
	
	@Column(name="k_desc")
	private String keywordDescription; // keyword description
	
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
	
public static void main(String[] args) {
		
        System.out.println("Hello World!");  
        
        SearchKeywords searchKeywordObj = new SearchKeywords();
        searchKeywordObj.setKeyword("Mechanical");
        searchKeywordObj.setKeywordDescription("Mechanical");
        
        
		Gson gson = new Gson();
		
		String value = gson.toJson(searchKeywordObj);
		System.out.println(value);
		
    }

}
