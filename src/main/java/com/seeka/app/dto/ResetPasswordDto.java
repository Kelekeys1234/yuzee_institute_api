package com.seeka.app.dto;import java.math.BigInteger;

import com.seeka.app.bean.UserDeviceInfo;

public class ResetPasswordDto {
	
	private UserDeviceInfo userDeviceInfo;
	private String oldPw;
	private String newPw;
	
	public UserDeviceInfo getUserDeviceInfo() {
		return userDeviceInfo;
	}
	public void setUserDeviceInfo(UserDeviceInfo userDeviceInfo) {
		this.userDeviceInfo = userDeviceInfo;
	}
	public String getOldPw() {
		return oldPw;
	}
	public void setOldPw(String oldPw) {
		this.oldPw = oldPw;
	}
	public String getNewPw() {
		return newPw;
	}
	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}
	 
}
