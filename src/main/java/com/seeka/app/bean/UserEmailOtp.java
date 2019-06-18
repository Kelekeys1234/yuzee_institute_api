package com.seeka.app.bean;import java.math.BigInteger;

// Generated 7 Jun, 2019 2:45:49 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserEmailOtp generated by hbm2java
 */
@Entity
@Table(name = "user_email_otp", catalog = "seeka_dev_v5")
public class UserEmailOtp implements java.io.Serializable {

	private BigInteger id;
	private String email;
	private long otp;
	private Date otpCreatedDate;
	private Date otpExpiryDate;
	private String status;

	public UserEmailOtp() {
	}

	public UserEmailOtp(String email, long otp, Date otpCreatedDate,
			Date otpExpiryDate, String status) {
		this.email = email;
		this.otp = otp;
		this.otpCreatedDate = otpCreatedDate;
		this.otpExpiryDate = otpExpiryDate;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@Column(name = "email", nullable = false, length = 245)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "otp", nullable = false)
	public long getOtp() {
		return this.otp;
	}

	public void setOtp(long otp) {
		this.otp = otp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_created_date", nullable = false, length = 19)
	public Date getOtpCreatedDate() {
		return this.otpCreatedDate;
	}

	public void setOtpCreatedDate(Date otpCreatedDate) {
		this.otpCreatedDate = otpCreatedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_expiry_date", nullable = false, length = 19)
	public Date getOtpExpiryDate() {
		return this.otpExpiryDate;
	}

	public void setOtpExpiryDate(Date otpExpiryDate) {
		this.otpExpiryDate = otpExpiryDate;
	}

	@Column(name = "status", nullable = false, length = 245)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
