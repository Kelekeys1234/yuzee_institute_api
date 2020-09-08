package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;

import lombok.Data;

@Data
public class StorageDto {
	@JsonProperty("entity_id")
	private String entityId;

	@JsonProperty("entity_type")
	private EntityTypeEnum entityType;

	@JsonProperty("entity_sub_type")
	private EntitySubTypeEnum entitySubType;

	@JsonProperty("stored_file_name")
	private String storedFileName;

	@JsonProperty("file_type")
	private String fileType;

	@JsonProperty("original_file_name")
	private String originalFileName;

	@JsonProperty("base_url")
	private String baseUrl;

	@JsonProperty("file_url")
	private String fileURL;
}
