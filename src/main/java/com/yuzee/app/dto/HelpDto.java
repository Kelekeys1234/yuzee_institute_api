package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HelpDto {

	@JsonProperty("help_id")
	private String id;
	
	@JsonProperty("title")
	@NotBlank(message = "title should not be blank")
	private String title;
	
	@JsonProperty("category_id")
	@NotBlank(message = "category_id should not be blank")
	private String categoryId;
	
	@JsonProperty("subCategory_id")
	@NotBlank(message = "subCategory_id should not be blank")
	private String subCategoryId;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("is_questioning")
	private Boolean isQuestioning;
	
	@JsonProperty("created_user")
	private String createdUser;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("assigned_user")
	private String assignedUser;
}
