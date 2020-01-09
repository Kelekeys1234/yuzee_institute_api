package com.seeka.app.dto;import java.math.BigInteger;

import com.seeka.app.bean.UserDeviceInfo;

public class LoginRequestDto {
	
	private UserDeviceInfo userDeviceInfo;
	private String email;
	private String password;
	
	public UserDeviceInfo getUserDeviceInfo() {
		return userDeviceInfo;
	}
	public void setUserDeviceInfo(UserDeviceInfo userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
