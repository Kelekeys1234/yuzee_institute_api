package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ConnectionExistDto {

	@JsonProperty("connection_exist")
	private boolean connectionExist;
}
