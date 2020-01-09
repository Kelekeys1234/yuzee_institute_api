package com.seeka.app.dto;import java.math.BigInteger;



import java.math.BigInteger;

import com.seeka.app.bean.UserInfo;
import com.seeka.app.bean.UserDeviceInfo;

public class SignUpRequestDto {
	
	private UserInfo user;
	private UserDeviceInfo userDeviceInfo;
	private String email;
	private Integer otp;
	private BigInteger currencyId;
	private String currencyName;
	
	

	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
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
	
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	
	public BigInteger getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigInteger currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	 
}
