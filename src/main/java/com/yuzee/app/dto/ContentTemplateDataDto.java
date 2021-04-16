package com.yuzee.app.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentTemplateDataDto implements Serializable {
	
	private static final long serialVersionUID = -7499404356358173135L;

	@JsonProperty("entityId")
	private String entityId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("target")
	private String target;
}
