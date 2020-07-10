package com.yuzee.app.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqRequestDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5744814923342867841L;

	@NotBlank(message = "title is required")
	@JsonProperty("title")
	private String title;
	
	@NotBlank(message = "description is required")
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("votes")
	private Integer votes;
	
	@NotBlank(message = "faq_category_id is required")
	@JsonProperty("faq_category_id")
	private String faqCategoryId;
	
	@NotBlank(message = "faq_sub_category_id is required")
	@JsonProperty("faq_sub_category_id")
	private String faqSubCategoryId;
	
	@NotBlank(message = "entity_id is required")
	@JsonProperty("entity_id")
	private String entityId;

	@NotBlank(message = "entity_type is required")
	@JsonProperty("entity_type")
	private String entityType;

	
}
