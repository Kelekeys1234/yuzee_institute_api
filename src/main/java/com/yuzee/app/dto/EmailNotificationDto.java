package com.yuzee.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailNotificationDto {

	@JsonProperty("payload")
	private EmailPayloadDto payload;
	
	@JsonProperty("channel")
	private String channel;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("tenant_code")
	private String tenantCode;
	
	@JsonProperty("notification_type")
	private String notificationType;
}
