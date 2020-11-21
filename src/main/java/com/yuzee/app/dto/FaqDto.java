package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class FaqDto {

	@JsonProperty(value = "faq_id", access = Access.READ_ONLY)
	private String id;

	@NotBlank(message = "title is required")
	@JsonProperty("title")
	private String title;

	@NotBlank(message = "description is required")
	@JsonProperty("description")
	private String description;

	@NotBlank(message = "entity_id is required")
	@JsonProperty("entity_id")
	private String entityId;

	@NotBlank(message = "entity_type is required")
	@JsonProperty("entity_type")
	private String entityType;

}
