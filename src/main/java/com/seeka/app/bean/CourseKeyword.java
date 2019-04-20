package com.seeka.app.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name="course_keywords")
public class CourseKeyword implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private UUID id;
	
	@Column(name="keyword")
	private String keyword; //keyword
	
	@Column(name="k_desc")
	private String keywordDescription; //keyword description
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
        
        CourseKeyword searchKeywordObj = new CourseKeyword();
        searchKeywordObj.setKeyword("Mechanical");
        searchKeywordObj.setKeywordDescription("Mechanical");
        
        
		Gson gson = new Gson();
		
		String value = gson.toJson(searchKeywordObj);
		System.out.println(value);
		
    }

}
