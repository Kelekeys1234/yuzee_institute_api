package com.seeka.app.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class UserViewDataRequestDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5780245895720098894L;
	private BigInteger userId;
	@NotNull(message = "entityId is required")
	private BigInteger entityId;
	@NotNull(message = "entityType is required")
	private String entityType;

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getEntityId() {
		return entityId;
	}

	public void setEntityId(final BigInteger entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(final String entityType) {
		this.entityType = entityType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (entityId == null ? 0 : entityId.hashCode());
		result = prime * result + (entityType == null ? 0 : entityType.hashCode());
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
		UserViewDataRequestDto other = (UserViewDataRequestDto) obj;
		if (entityId == null) {
			if (other.entityId != null) {
				return false;
			}
		} else if (!entityId.equals(other.entityId)) {
			return false;
		}
		if (entityType == null) {
			if (other.entityType != null) {
				return false;
			}
		} else if (!entityType.equals(other.entityType)) {
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
		builder.append("UserViewDataRequestDto [userId=").append(userId).append(", entityId=").append(entityId).append(", entityType=").append(entityType)
				.append("]");
		return builder.toString();
	}

}
