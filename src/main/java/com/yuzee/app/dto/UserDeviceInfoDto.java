package com.yuzee.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDeviceInfoDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8433234322653635466L;
	private Long id;
	private Long userId;
	private String deviceId;
	private String sdkVersionCode;
	private String appVersion;
	private String osVersion;
	private String model;
	private String platform;
	private String ipAddress;
	private boolean pushNotification;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private Long userSessionId;
}
