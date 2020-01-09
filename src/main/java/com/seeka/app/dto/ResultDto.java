package com.seeka.app.dto;

import java.io.Serializable;

public class ResultDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1621934015243442407L;

	private Object data;
	private String status;
	private String message;

	public Object getData() {
		return data;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultDto [data=").append(data).append(", status=").append(status).append(", message=").append(message).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (data == null ? 0 : data.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (status == null ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ResultDto other = (ResultDto) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}

}
