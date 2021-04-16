package com.yuzee.app.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NotificationMetaDataDto implements Serializable {

	private static final long serialVersionUID = 1164408510616362638L;

	@JsonProperty("target_entity_Id")
	private String targetEntityId;
	
	@JsonProperty("content_template")
	private String contentTemplate;
	
	@JsonProperty("content_template_data")
	private List<ContentTemplateDataDto> contentTemplateData;
}
