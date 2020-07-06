package com.seeka.app.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserViewCourseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("trasanction_id")
	private String id;
	
	@JsonProperty("entity_id")
	@NotBlank(message = "entity_id should not be blank")
	private String entityId;
	
	@JsonProperty("entity_type")
	@NotBlank(message = "entity_type should not be blank")
	private String entityType;
	
	@JsonProperty("user_id")
	@NotBlank(message = "user_id should not be blank")
	private String userId;
	
	@JsonProperty("transaction_type")
	@NotBlank(message = "transaction_type should not be blank")
	private String transactionType;

}
