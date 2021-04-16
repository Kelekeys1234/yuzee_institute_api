package com.yuzee.app.dto;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NotificationTemplateDataDto implements Serializable {
	
	private static final long serialVersionUID = 6985949253555335556L;
	
	@NotEmpty(message = "notification_template_type is required")
	@JsonProperty("notification_template_type")
	private String notificationTemplateType;

	@NotEmpty(message = "user_id is required")
	@JsonProperty("user_id")
	private String userId;
	
	@Valid
	@JsonProperty("email-details")
	private EmailAddressDto emailDetails;
	
	@NotNull(message = "notification_metadata is required")
	@JsonProperty("notification_metadata")
	private NotificationMetaDataDto notificationMetadata;
	
	@JsonProperty("sender_user_id")
	private String senderUserId;
}
