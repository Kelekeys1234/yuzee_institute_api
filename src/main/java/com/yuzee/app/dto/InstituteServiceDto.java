package com.yuzee.app.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.yuzee.app.bean.Service;
import com.yuzee.common.lib.dto.storage.StorageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class InstituteServiceDto {

	@JsonProperty("institute_service_id")
	private String instituteServiceId;

	@NotNull(message = "{service.is_required}")
	@JsonProperty("service")
	private ServiceDto service;

	@JsonProperty("description")
	private String description;

	@JsonProperty(value = "media", access = Access.READ_ONLY)
	private List<StorageDto> media;

	public InstituteServiceDto(String instituteServiceId, ServiceDto service, String description) {
		this.instituteServiceId = instituteServiceId;
		this.service = service;
		this.description = description;
	}
}
