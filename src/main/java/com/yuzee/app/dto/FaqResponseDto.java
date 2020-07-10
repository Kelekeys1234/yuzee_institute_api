package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FaqResponseDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5744814923342867841L;

	@JsonProperty("faq_id")
	private String id;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("votes")
	private Integer votes;
	
	@JsonProperty("faq_categor_id")
	private String faqCategoryId;
	
	@JsonProperty("faq_categor_name")
	private String faqCategoryName;
	
	@JsonProperty("faq_sub_category_id")
	private String faqSubCategoryId;
	
	@JsonProperty("faq_sub_category_name")
	private String faqSubCategoryName;

}
