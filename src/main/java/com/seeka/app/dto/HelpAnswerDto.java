package com.seeka.app.dto;

import java.math.BigInteger;

public class HelpAnswerDto {

	private BigInteger userId;
	private BigInteger helpId;
	private String answer;
	private String createdBy;
	private String updatedBy;

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getHelpId() {
		return helpId;
	}

	public void setHelpId(BigInteger helpId) {
		this.helpId = helpId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
