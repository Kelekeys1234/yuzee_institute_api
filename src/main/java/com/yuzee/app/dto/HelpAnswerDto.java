package com.yuzee.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HelpAnswerDto {

	@JsonProperty("user_id")
	@NotBlank(message = "user_id should not be blank")
    private String userId;
	
	@JsonProperty("help_id")
	@NotBlank(message = "help_id should not be blank")	
    private String helpId;
	
	@JsonProperty("answer")
	@NotBlank(message = "answer should not be blank")
    private String answer;
	
	@JsonProperty("file_url")
    private String fileUrl;
}
