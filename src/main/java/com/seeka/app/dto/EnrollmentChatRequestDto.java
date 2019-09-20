package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class EnrollmentChatRequestDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7590305810325611394L;
	private BigInteger id;
	private BigInteger enrollmentId;
	private BigInteger userId;
	/**
	 * SEEKA/USER
	 */
	private String initiateFrom;
	/**
	 * UserId
	 */
	private BigInteger initiateFromId;
	private String message;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	public BigInteger getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(final BigInteger enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public String getInitiateFrom() {
		return initiateFrom;
	}

	public void setInitiateFrom(final String initiateFrom) {
		this.initiateFrom = initiateFrom;
	}

	public BigInteger getInitiateFromId() {
		return initiateFromId;
	}

	public void setInitiateFromId(final BigInteger initiateFromId) {
		this.initiateFromId = initiateFromId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enrollmentId == null ? 0 : enrollmentId.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (initiateFrom == null ? 0 : initiateFrom.hashCode());
		result = prime * result + (initiateFromId == null ? 0 : initiateFromId.hashCode());
		result = prime * result + (message == null ? 0 : message.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
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
		EnrollmentChatRequestDto other = (EnrollmentChatRequestDto) obj;
		if (enrollmentId == null) {
			if (other.enrollmentId != null) {
				return false;
			}
		} else if (!enrollmentId.equals(other.enrollmentId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (initiateFrom == null) {
			if (other.initiateFrom != null) {
				return false;
			}
		} else if (!initiateFrom.equals(other.initiateFrom)) {
			return false;
		}
		if (initiateFromId == null) {
			if (other.initiateFromId != null) {
				return false;
			}
		} else if (!initiateFromId.equals(other.initiateFromId)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnrollmentChatRequestDto [id=").append(id).append(", enrollmentId=").append(enrollmentId).append(", userId=").append(userId)
				.append(", initiateFrom=").append(initiateFrom).append(", initiateFromId=").append(initiateFromId).append(", message=").append(message)
				.append("]");
		return builder.toString();
	}

}
